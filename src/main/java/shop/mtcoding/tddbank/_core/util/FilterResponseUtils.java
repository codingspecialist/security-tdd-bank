package shop.mtcoding.tddbank._core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.erros.exception.Exception403;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse resp, Exception401 e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception403 e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }
}
