package com.agcity.swan.cglib.demo;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description: todo(cglib动态生成实体bean)
 * @author: agcity@qq.com
 * @date : 2018/12/26 19:15
 * @version: 1.0
 */
public class CglibBean {

    /**
     * 实体Object
     */
    public Object object = null;

    /**
     * 属性map
     */
    public BeanMap beanMap = null;

    public CglibBean(){
        super();
    }

    public CglibBean(Map<String,Class> propertyMap){
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    /**
     * @Description: todo(给Bean属性赋值)
     * @param property
     * @param value
     * @return :
     * @author : agcity@qq.com
     * @date : 2018/12/26 19:20
     * @version: 1.0
     */
    public void setValue(String property, Object value){
        beanMap.put(property,value);
    }

    /**
     * @description: todo(通过属性名拿属性值)
     * @param property
     * @return :
     * @author : agcity@qq.com
     * @date : 2018/12/26 19:22
     * @version: 1.0
     */
    public Object getValue(String property){
        return beanMap.get(property);
    }

    public Object getObject(){
        return this.object;
    }

    /**
     * @description: todo(生成bean)
     * @param propertyMap
     * @return :
     * @author : agcity@qq.com
     * @date : 2018/12/26 19:28
     * @version: 1.0
     */
    private Object generateBean(Map<String, Class> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set<Map.Entry<String, Class>>  keySet = propertyMap.entrySet();
        for( Map.Entry<String, Class> property : propertyMap.entrySet()){
            generator.addProperty(property.getKey().replace(" ", ""), property.getValue());//replace(" ", "")去掉字段中包含的空格
        }
       return generator.create();
    }


    //测试
    public static void main(String[] args) throws ClassNotFoundException {
        //定义属性
        Map<String,Class> propertyMap = new HashMap<>();
        propertyMap.put("id",Class.forName("java.lang.Integer"));
        propertyMap.put("name",Class.forName("java.lang.String"));
        propertyMap.put("email", Class.forName("java.lang.String"));

        //生成bean, 属性和getter setter方法
        CglibBean bean = new CglibBean(propertyMap);

        //给bean赋值
        bean.setValue("id", new Integer("123"));
        bean.setValue("name", "张三");
        bean.setValue("email", "agcity@qq.ocm");

        Object obj = bean.getObject();
        System.out.println(obj);//net.sf.cglib.empty.Object$$BeanGeneratorByCGLIB$$82e70002@123772c4

        //反射
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(obj.getClass());

        for(Method method: methods){
            System.out.println(method.getName());
        }


        HashMap<String, Class> pMap = new HashMap<String, Class>();
        pMap.put("response",DataType.STR.getClazz());//用于保存处理结果
        pMap.put("responseMap",DataType.MAP.getClazz());//用于保存返回参数
        pMap.put("responseList",DataType.LIST.getClazz());//用于保存返回参数
        pMap.put("responseCode",DataType.INT.getClazz());//用于存放返回状态   0-成功,1-失败,2-超时  3-规则模板更新中
        pMap.put("responseScoreList",DataType.LIST.getClazz());//评分卡中,用于存放每个字段对应的分数
        pMap.put("responseScoreDetailList",DataType.LIST.getClazz());//评分卡的打分明细

        CglibBean cgbean = new CglibBean(pMap);

        System.out.println(cgbean);

        Object oj = cgbean.getObject();

        System.out.println(oj);
        System.out.println(oj.getClass());


        Method[] mtds = ReflectionUtils.getAllDeclaredMethods(oj.getClass());

        for(Method method: mtds){
            System.out.println(method.getName());
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Parameter[] parameters = method.getParameters();
            System.out.println();
        }
    }
}
