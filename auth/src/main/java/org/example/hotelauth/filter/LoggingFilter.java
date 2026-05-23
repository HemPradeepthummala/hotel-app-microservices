package org.example.hotelauth.filter;


import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    logger.info("From logger");

    long start = System.currentTimeMillis();
    try {
      chain.doFilter(request, response);
    } finally {
      long end = System.currentTimeMillis() - start;
      System.out.println(response);
      logger.info("total time taken: {}", end);
    }
  }
}
