package kok.spring21.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import javax.servlet.Filter;

//для настройки диспетчер-сервлета java-кодом:
class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	@Override
    protected Class<?>[] getRootConfigClasses(){
		return new Class[]{PersistenceConfig.class,SecurityConfig.class};
    }
	@Override
    protected Class<?>[] getServletConfigClasses(){
		return new Class[]{SpringConfig.class};
    }
	@Override
    protected String[] getServletMappings(){
		return new String[] {"/"};
    }

	@Override
    protected Filter[] getServletFilters(){
		return new Filter[] {new HiddenHttpMethodFilter()};
    }
}
