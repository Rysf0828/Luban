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
 * @date 2020/11/27
 */

public class LubanApplicationContext {

    private ConcurrentHashMap<String,Object> singletonObjects=new ConcurrentHashMap();
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList();


    public LubanApplicationContext(Class configClass) {
//        扫描，生成beanDefinition,添加到beanDefinitionMap
        scan(configClass);
//        非懒加载单例bean放入单例池
        instanceSingletonBean();
    }

    /**
     * 扫描，并生成beanDefinitionMap
     * @param configClass
     */
    public void scan(Class configClass){
        ComponentScan componentScan = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
        String scanPath = componentScan.value().replace(".","/");
//        扫描出所有的class文件
        List<Class> classList = genBeanClasses(scanPath);
        for (Class clazz : classList) {
//            是否是bean
            if(clazz.isAnnotationPresent(Component.class)){
                Component component = (Component) clazz.getAnnotation(Component.class);
                String beanName = component.value();
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClass(clazz);

                if(clazz.isAnnotationPresent(Scope.class)&&ScopeEnum.prototype.name().equals(((Scope)clazz.getAnnotation(Scope.class)).value())){
                    beanDefinition.setScope(ScopeEnum.prototype);
                }
                if(clazz.isAnnotationPresent(Lazy.class)){
                    beanDefinition.setLazy(true);
                }else{
                    beanDefinition.setLazy(false);
                }
                beanDefinitionMap.put(beanName,beanDefinition);

                if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                    beanPostProcessorList.add((BeanPostProcessor)doCreatBean(beanName,beanDefinition));
                }
            }
        }
    }

    /**
     * 获取路径下所有class文件
     * @param scanPath
     * @return
     */
    private List<Class> genBeanClasses(String scanPath) {
        List<Class> classList = new ArrayList();
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(scanPath);
        File fileRoot = new File(resource.getFile());
        genAllClasses(classLoader,classList, fileRoot);
        return classList;
    }

    /**
     * 递归获取所有class文件，放入classList
     * @param classLoader
     * @param classList
     * @param fileRoot
     */
    private void genAllClasses(ClassLoader classLoader,List<Class> classList,File fileRoot){
        for (File file : fileRoot.listFiles()) {
            if(file.isDirectory()){
                genAllClasses(classLoader,classList,file);
            }else{
                String filePath = file.getAbsolutePath();
                if (filePath.endsWith(".class")) {
                    String className = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class")).replace("\\",".");
                    try {
                        Class aClass = classLoader.loadClass(className);
                        classList.add(aClass);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 实例化非懒加载单例bean
     */
    private void instanceSingletonBean() {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals(ScopeEnum.singleton)&&!beanDefinition.isLazy()){
                Object bean = doCreatBean(beanName, beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }
    }

    /**
     * 创建bean
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object doCreatBean(String beanName,BeanDefinition beanDefinition) {
        try {
            Class beanClass = beanDefinition.getBeanClass();
            Constructor declaredConstructor = beanClass.getDeclaredConstructor();
            Object instance = declaredConstructor.newInstance();
            for (Field field : beanClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    String fieldName = field.getName();
                    Object bean = getBean(fieldName);
                    field.setAccessible(true);
                    field.set(instance,bean);
                }
            }

            if(instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            if(instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropetiesSet();
            }
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessAfterInitialization(beanName,instance);
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
        return null;
    }


    /**
     * 获取bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        if(singletonObjects.containsKey(beanName)){
            return singletonObjects.get(beanName);
        }else{
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            return doCreatBean(beanName,beanDefinition);
        }
    }
}
