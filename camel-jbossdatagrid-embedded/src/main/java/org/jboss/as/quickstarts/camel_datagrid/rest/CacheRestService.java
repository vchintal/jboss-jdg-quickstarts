package org.jboss.as.quickstarts.camel_datagrid.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.as.quickstarts.camel_datagrid.pojos.PersonPojo;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class CacheRestService
 */
@WebServlet("/CacheRestService")
public class CacheRestService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CacheRestService() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext context;
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        
        EmbeddedCacheManager cm = (EmbeddedCacheManager) context.getBean("cacheManager");
        Cache<String, PersonPojo> cache = cm.getCache("camel-cache");
        
        // Call to cache.values() is strongly *NOT* recommended. It is done 
        // here for convenience as this project is meant to be a demo
        String json = new Gson().toJson(new ArrayList<PersonPojo>(cache.values()));
        response.getWriter().write(json);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
