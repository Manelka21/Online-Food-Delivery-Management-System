package com.fooddelivery.servlet;

// MenuItemServlet - routes HTTP requests to CRUD operations

import com.fooddelivery.model.MenuItem;
import com.fooddelivery.service.MenuItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/menuItems")
public class MenuItemServlet extends HttpServlet {

    private final MenuItemService service = new MenuItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "add":
                req.getRequestDispatcher("/WEB-INF/views/menuItem/add.jsp")
                        .forward(req, resp);
                break;

            case "edit": {
                String id = req.getParameter("id");
                MenuItem item = service.getById(id);
                if (item == null) {
                    resp.sendRedirect(req.getContextPath() + "/menuItems");
                    return;
                }
                req.setAttribute("item", item);
                req.getRequestDispatcher("/WEB-INF/views/menuItem/edit.jsp")
                        .forward(req, resp);
                break;
            }

            case "delete": {
                String id = req.getParameter("id");
                MenuItem item = service.getById(id);
                if (item == null) {
                    resp.sendRedirect(req.getContextPath() + "/menuItems");
                    return;
                }
                req.setAttribute("item", item);
                req.getRequestDispatcher("/WEB-INF/views/menuItem/delete.jsp")
                        .forward(req, resp);
                break;
            }

            case "search": {
                String q = req.getParameter("q");
                List<MenuItem> results = (q != null && !q.isBlank())
                        ? service.search(q) : service.getAll();
                req.setAttribute("items", results);
                req.setAttribute("query", q);
                req.getRequestDispatcher("/WEB-INF/views/menuItem/list.jsp")
                        .forward(req, resp);
                break;
            }

            default: {
                String category = req.getParameter("category");
                List<MenuItem> items = (category != null && !category.isBlank())
                        ? service.getByCategory(category) : service.getAll();
                req.setAttribute("items", items);
                req.setAttribute("categories", service.getAllCategories());
                req.getRequestDispatcher("/WEB-INF/views/menuItem/list.jsp")
                        .forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "create": {
                MenuItem item = buildFromRequest(req);
                service.create(item);
                resp.sendRedirect(req.getContextPath()
                        + "/menuItems?success=Item+added+successfully");
                break;
            }

            case "update": {
                MenuItem item = buildFromRequest(req);
                item.setId(req.getParameter("id"));
                service.update(item);
                resp.sendRedirect(req.getContextPath()
                        + "/menuItems?success=Item+updated+successfully");
                break;
            }

            case "delete": {
                String id = req.getParameter("id");
                service.delete(id);
                resp.sendRedirect(req.getContextPath()
                        + "/menuItems?success=Item+deleted+successfully");
                break;
            }

            default:
                resp.sendRedirect(req.getContextPath() + "/menuItems");
        }
    }

    private MenuItem buildFromRequest(HttpServletRequest req) {
        MenuItem item = new MenuItem();
        item.setName(req.getParameter("name"));
        item.setCategory(req.getParameter("category"));
        item.setDescription(req.getParameter("description"));
        item.setPrice(Double.parseDouble(req.getParameter("price")));
        item.setAvailable("on".equals(req.getParameter("available"))
                || "true".equals(req.getParameter("available")));
        item.setImageUrl(req.getParameter("imageUrl"));
        return item;
    }
}
