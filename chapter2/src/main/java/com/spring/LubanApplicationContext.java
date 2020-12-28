package com.spring;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hyy
 * @title: LubanApplicationContext
 * @description: TODO
 * @date 2020/11/30
 */

public class LubanApplicationContext {


    private ConcurrentHashMap<String ,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap();
    private ConcurrentHashMap<String ,Object> singletonObject=new ConcurrentHashMap();
    private List<BeanPostProcessor> beanPostProcessorList=new ArrayList();

    public LubanApplicationContext(Class configClass) {
        scan(configClass);
        instanceNonLazySingletonBean();
    }

    public void instanceNonLazySingletonBean(){
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(ScopeEnum.singleton.name().equals(beanDefinition.getScope())&&!beanDefinition.isLazy()){
                Object o = doCreateBran(beanName, beanDefinition);
                singletonObject.put(beanName,o);
            }
        }
    }

    public void scan(Class configClass) {
        if(configClass.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScan = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
            String value = componentScan.value();
            String scanPath = value.replace(".", "/");
            List<Class> classList=genClassList(scanPath);
            for (Class clazz  : classList){
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component component = (Component)clazz.getAnnotation(Component.class);
                    String beanName = component.value();
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setBeanName(beanName);
                    beanDefinition.setBeanClass(clazz);
                    if (clazz.isAnnotationPresent(Scope.class)) {
                        Scope scope = (Scope)clazz.getAnnotation(Scope.class);
                        beanDefinition.setScope(scope.value());
                    }else{
                        beanDefinition.setScope(ScopeEnum.singleton.name());
                    }
                    if(clazz.isAnnotationPresent(Lazy.class)){
                        Lazy lazy = (Lazy)clazz.getAnnotation(Lazy.class);
                        beanDefinition.setLazy(true);
                    }else{
                        beanDefinition.setLazy(false);
                    }
                    beanDefinitionMap.put(beanName,beanDefinition);

                    if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                        beanPostProcessorList.add((BeanPostProcessor)doCreateBran(beanName,beanDefinition));
                    }
                }
            }
        }
    }

    public List<Class> genClassList(String scanPath) {
        List<Class> classList = new ArrayList();
        URL resource = this.getClass().getClassLoader().getResource(scanPath);
        File fileRoot = new File(resource.getFile());
        fillAllClass(classList,fileRoot);
        return classList;
    }

    public void fillAllClass(List<Class> classList,File fileRoot){
        if (fileRoot.isDirectory()) {
            for (File file : fileRoot.listFiles()) {
                fillAllClass(classList,file);
            }
        }else{
            String path = fileRoot.getAbsolutePath();
            String clazz = path.replace("\\",".").substring(path.indexOf("com"), path.indexOf(".class"));
            ClassLoader classLoader = this.getClass().getClassLoader();
            try {
                Class<?> aClass = classLoader.loadClass(clazz);
                classList.add(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Object doCreateBran(String beanName,BeanDefinition beanDefinition){
        try {
            Class beanClass = beanDefinition.getBeanClass();
            Constructor constructor = beanClass.getDeclaredConstructor();
            Object instance = constructor.newInstance();
            for (Field field : beanClass.getDeclaredFields()) {
                if(field.isAnnotationPresent(Autowired.class)){
                    String fieldName = field.getName();
                    Object o = getBean(fieldName);
                    field.setAccessible(true);
                    field.set(instance,o);
                }
            }


            if(instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }

//            if (InitializingBean.class.isAssignableFrom(beanClass)) {
//                ((InitializingBean)instance).afterPropertiesSet();
//            }
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(beanName,instance);
            }


            return instance;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public Object getBean(String beanName){
//        单例非懒加载
        if(singletonObject.containsKey(beanName)){
            return singletonObject.get(beanName);
        }else{
            if(beanDefinitionMap.containsKey(beanName)){
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                Object bean = doCreateBran(beanName, beanDefinition);
                return bean;
            }else{
                return  null;
            }
        }
    }
}
