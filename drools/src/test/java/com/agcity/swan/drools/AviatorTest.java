package com.agcity.swan.drools;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AviatorTest {

    private static final Logger logger = LoggerFactory.getLogger(AviatorTest.class);

    @Test
    public void test1(){ //算术计算
        Object result = AviatorEvaluator.execute("(1 + 2 + 3) / 2");
        logger.info("===>计算结果: {}", result);
    }

    @Test
    public void test2(){//逻辑运算
        Boolean result = (Boolean)AviatorEvaluator.execute("1+(2+3) > 7 && 8+4 == 12");
        logger.info("===>计算结果: {}", result);
    }

    @Test
    public void test3(){ //变量替换
        String name = "唐简";
        Map<String,Object> env = new HashMap<>();
        env.put("name", name);
        String result = (String) AviatorEvaluator.execute(" 'Hello ' + name ", env);
        logger.info("===>计算结果: {}", result);
    }

    @Test
    public void test4(){ //内置函数
        String str = "小哥哥带你使用Aviator";
        Map<String,Object> env = new HashMap<>();
        env.put("str",str);
        Long result = (Long)AviatorEvaluator.execute("string.length(str)",env);
        logger.info("===>计算结果: {}", result);
    }

    @Test
    public void test5(){ //compile
        String expression = "a-(b-c)>100";
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        Boolean result = (Boolean) compiledExp.execute(env);
        logger.info("===>计算结果: {}", result);
        AviatorEvaluator.invalidateCache(expression);
    }

    @Test
    public void test6(){
        AviatorEvaluator.addFunction(new MinFunction());
        String expression = "min(a,b)";
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        Double result = (Double) compiledExp.execute(env);
        logger.info("===>计算结果: {}", result);
    }

    //编译脚本文件
    @Test
    public void test7() throws IOException {
        Expression expression = AviatorEvaluator.getInstance().compileScript("examples/hello.av");
        expression.execute();
    }

    //编译脚本
    @Test
    public void test8() throws IOException {
        Expression expression = AviatorEvaluator.getInstance().compile("println(\"hello , AviatorScript!\")");
        expression.execute();
    }

    @Test
    public void validate(){
        try{
            AviatorEvaluator.validate(" 1+*2=19");
        }catch (Exception e){
            logger.info("===>计算结果: {}", e.getMessage());
        }
    }

    @Test
    public void exe() throws IOException {
        Expression expression = AviatorEvaluator.getInstance().compileScript("examples/var.av");
        Object result = expression.execute();
        logger.info("===>计算结果: {}", result);
    }

}



