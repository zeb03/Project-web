package javaweb.ssm.myspringmvc;

import javaweb.ssm.ioc.BeanFactor;
import javaweb.ssm.ioc.ClassPathXmlApplicationContext;
import javaweb.ssm.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author ze
 * @creat 2022-10-30-10:31
 */
/*
拦截请求，获取请求名称，分配到对应类（使用xml配置文件）
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactor beanFactor;
    public DispatcherServlet() {
        beanFactor = new ClassPathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setCharacterEncoding("utf8");
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastDotIndex);//组件名称id
        Object controllerBeanObj = beanFactor.getBean(servletPath);//根据id获取到对应的类

        String operate = req.getParameter("operate");
        if (!StringUtils.isNotEmpty(operate)) {
            operate = "index";
        }
        //通过反射代替switch
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method: methods){
                if (operate.equals(method.getName())){
                    //通过反射获取参数名称，通过请求获取每个参数的值
                    Parameter[] parameters = method.getParameters();
                    Object[] parametersValue = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)){
                            parametersValue[i] = req;
                        }else if ("resp".equals(parameterName)){
                            parametersValue[i] = resp;
                        }else if ("session".equals(parameterName)){
                            parametersValue[i] = req.getSession();
                        }else {
                            String parameterValue = req.getParameter(parameterName);
                            //注意获取到的值为string
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if (parameterObj != null){
                                if ("java.lang.Integer".equals(typeName)){
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parametersValue[i] = parameterObj;
                        }
                    }

                    //方法调用
                    method.setAccessible(true);
                    Object methodReturnObj = method.invoke(controllerBeanObj, parametersValue);

                    //视图处理(解决重定向和渲染)
                    if (methodReturnObj != null) {
                        String returnStr = (String) methodReturnObj;
                        if (returnStr.startsWith("redirect:")){
                            String redirectStr = returnStr.substring("redirect:".length());
                            resp.sendRedirect(redirectStr);
                        }else {
                            super.processTemplate(returnStr,req,resp);
                        }
                    }
                }
            }

            /*else {
                throw new RuntimeException("operate不合法");
            }*/
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
