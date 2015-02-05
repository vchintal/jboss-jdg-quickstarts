package org.jboss.as.quickstarts.camel_infinispan.listener;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.inject.Inject; 
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.camel.component.gson.GsonDataFormat;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.as.quickstarts.camel_infinispan.pojos.PersonPojo;

public class JndiServletContextListener implements ServletContextListener {

    @Inject
    @Named("cacheManager")
    private EmbeddedCacheManager cacheManager;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) { 
        InitialContext ic;
        try {        
            ic = new InitialContext(); 
            ic.bind("cacheManager", cacheManager);
            
            // Couldn't find any better place to instantiate and bind this data format  
            // to the ic
            GsonDataFormat json = new GsonDataFormat();
            json.setUnmarshalType(PersonPojo.class);
            ic.bind("json", json);
        } catch(NamingException ne) {
        
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { 
        if(cacheManager != null) {
            cacheManager.stop();
        }                
    }

}
