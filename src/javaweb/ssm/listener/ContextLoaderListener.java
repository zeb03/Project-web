package javaweb.ssm.listener;

import javaweb.ssm.ioc.BeanFactor;
import javaweb.ssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author ze
 * @creat 2022-11-05-14:19
 */
//监听上下文启动，在启动时创建IOC容器（beanFactor），并将其保存到application作用域
//中央控制器从application获取beanFactor
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //获取上下文
        ServletContext application = servletContextEvent.getServletContext();
        //获取path参数
        String path = application.getInitParameter("contextConfigLocation");
        //创建IOC容器
        BeanFactor beanFactor = new ClassPathXmlApplicationContext(path);
        application.setAttribute("beanFactor",beanFactor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
