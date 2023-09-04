package com.project.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            
            String contextPath = request.getContextPath(); // /ROOT
            String path = request.getServletPath(); // /api/student2/update.json
            String query = request.getQueryString();

            log.info("{}, {}, {}, {}", contextPath, path, query, request.getRequestURL().toString().split("/")[2] );
            String requestUrl = request.getRequestURL().toString().split("/")[2];

            if(path.length()<2){
                response.sendRedirect("http://" + requestUrl + contextPath+ "/member/main.do");
            }
            

            // if (!path.contains("login") && path.contains("logout")) {
            //     HttpSession httpSession = request.getSession();

            //     if (query == null) {
            //         httpSession.setAttribute("url", path);
            //     } else {
            //         httpSession.setAttribute("url", path + "?" + query);
            //     }
            // }           

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}