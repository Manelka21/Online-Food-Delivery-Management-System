<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Menu Items — FoodRush</title>
  <link rel="preconnect" href="https://fonts.googleapis.com"/>
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;900&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet"/>
  <style>
    :root {
      --bg:       #0d0d0d;
      --surface:  #161616;
      --card:     #1e1e1e;
      --border:   #2a2a2a;
      --accent:   #f97316;
      --accent2:  #fb923c;
      --text:     #f5f0eb;
      --muted:    #7a7370;
    }
    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      background: var(--bg);
      color: var(--text);
      font-family: 'DM Sans', sans-serif;
      font-weight: 300;
      min-height: 100vh;
    }
    nav {
      background: var(--surface);
      border-bottom: 1px solid var(--border);
      padding: 0 2rem;
      height: 64px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      position: sticky;
      top: 0;
      z-index: 100;
    }
    .brand {
      font-family: 'Playfair Display', serif;
      font-size: 1.5rem;
      font-weight: 900;
      color: var(--accent);
    }
    .brand span { color: var(--text); }
    .nav-add {
      background: var(--accent);
      color: #fff;
      text-decoration: none;
      padding: .55rem 1.25rem;
      border-radius: 8px;
      font-weight: 500;
      font-size: .875rem;
      transition: background .2s;
    }
    .nav-add:hover { background: var(--accent2); }
    .container { max-width: 1200px; margin: 0 auto; padding: 2rem; }
    .page-header {
      display: flex;
      align-items: flex-end;
      justify-content: space-between;
      margin-bottom: 2rem;
      gap: 1rem;
      flex-wrap: wrap;
    }
    .page-title {
      font-family: 'Playfair Display', serif;
      font-size: 2.5rem;
      font-weight: 700;
      line-height: 1;
    }
    .page-title span { color: var(--accent); }
    .item-count { color: var(--muted); font-size: .875rem; margin-top: .25rem; }
    .flash {
      background: #14532d;
      border: 1px solid #16a34a;
      color: #bbf7d0;
      padding: .75rem 1.25rem;
      border-radius: 8px;
      margin-bottom: 1.5rem;
      font-size: .875rem;
    }
    .toolbar {
      display: flex;
      gap: .75rem;
      margin-bottom: 2rem;
      flex-wrap: wrap;
    }
    .search-wrap { position: relative; flex: 1; min-width: 200px; }
    .search-wrap svg {
      position: absolute;
      left: .9rem;
      top: 50%;
      transform: translateY(-50%);
      color: var(--muted);
      pointer-events: none;
    }
    .search-input {
      width: 100%;
      background: var(--surface);
      border: 1px solid var(--border);
      color: var(--text);
      padding: .6rem .9rem .6rem 2.5rem;
      border-radius: 8px;
      font-family: inherit;
      font-size: .9rem;
      outline: none;
      transition: border-color .2s;
    }
    .search-input:focus { border-color: var(--accent); }
    .filter-select {
      background: var(--surface);
      border: 1px solid var(--border);
      color: var(--text);
      padding: .6rem 1rem;
      border-radius: 8px;
      font-family: inherit;
      font-size: .9rem;
      outline: none;
      cursor: pointer;
    }
    .search-btn {
      background: var(--accent);
      color: #fff;
      border: none;
      padding: .6rem 1.25rem;
      border-radius: 8px;
      font-family: inherit;
      font-size: .9rem;
      font-weight: 500;
      cursor: pointer;
      transition: background .2s;
    }
    .search-btn:hover { background: var(--accent2); }
    .table-wrap {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: 12px;
      overflow: hidden;
    }
    table { width: 100%; border-collapse: collapse; }
    thead th {
      background: var(--surface);
      padding: .875rem 1.25rem;
      text-align: left;
      font-size: .75rem;
      font-weight: 500;
      letter-spacing: .08em;
      text-transform: uppercase;
      color: var(--muted);
      border-bottom: 1px solid var(--border);
    }
    tbody tr {
      border-bottom: 1px solid var(--border);
      transition: background .15s;
    }
    tbody tr:last-child { border-bottom: none; }
    tbody tr:hover { background: rgba(249,115,22,.04); }
    td { padding: 1rem 1.25rem; font-size: .9rem; vertical-align: middle; }
    .item-name { font-weight: 500; color: var(--text); }
    .item-desc {
      color: var(--muted);
      font-size: .8rem;
      max-width: 260px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .category-badge {
      background: rgba(249,115,22,.15);
      color: var(--accent);
      padding: .2rem .65rem;
      border-radius: 20px;
      font-size: .78rem;
      font-weight: 500;
    }
    .price { font-family: 'Playfair Display', serif; font-weight: 700; }
    .status-badge { padding: .2rem .65rem; border-radius: 20px; font-size: .78rem; font-weight: 500; }
    .status-badge.available   { background: #14532d; color: #86efac; }
    .status-badge.unavailable { background: #450a0a; color: #fca5a5; }
    .actions { display: flex; gap: .5rem; }
    .btn-edit, .btn-delete {
      padding: .35rem .85rem;
      border-radius: 6px;
      font-size: .8rem;
      font-weight: 500;
      text-decoration: none;
      transition: opacity .2s;
    }
    .btn-edit   { background: #1e3a5f; color: #93c5fd; }
    .btn-delete { background: #450a0a; color: #fca5a5; }
    .btn-edit:hover, .btn-delete:hover { opacity: .8; }
    .empty { text-align: center; padding: 4rem 2rem; color: var(--muted); }
    .empty p { font-size: 1.1rem; margin-bottom: 1.5rem; margin-top: 1rem; }
    .empty a { color: var(--accent); text-decoration: none; font-weight: 500; }
  </style>
</head>
<body>

<nav>
  <div class="brand">Food<span>Rush</span></div>
  <a href="${pageContext.request.contextPath}/menuItems?action=add" class="nav-add">+ Add Item</a>
</nav>

<div class="container">

  <c:if test="${not empty param.success}">
    <div class="flash">✓ ${param.success}</div>
  </c:if>

  <div class="page-header">
    <div>
      <h1 class="page-title">Menu <span>Items</span></h1>
      <p class="item-count">
        <c:choose>
          <c:when test="${not empty items}">${items.size()} item(s) found</c:when>
          <c:otherwise>No items</c:otherwise>
        </c:choose>
      </p>
    </div>
  </div>

  <form method="get" action="${pageContext.request.contextPath}/menuItems" class="toolbar">
    <input type="hidden" name="action" value="search"/>
    <div class="search-wrap">
      <svg width="16" height="16" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <circle cx="11" cy="11" r="8"/><path stroke-linecap="round" d="m21 21-4.35-4.35"/>
      </svg>
      <input class="search-input" type="text" name="q"
             placeholder="Search by name, category or description…"
             value="${not empty query ? query : ''}"/>
    </div>
    <select class="filter-select" name="category"
            onchange="this.form.action='${pageContext.request.contextPath}/menuItems';this.form.submit()">
      <option value="">All Categories</option>
      <c:forEach items="${categories}" var="cat">
        <option value="${cat}">${cat}</option>
      </c:forEach>
    </select>
    <button class="search-btn" type="submit">Search</button>
  </form>

  <div class="table-wrap">
    <c:choose>
      <c:when test="${not empty items}">
        <table>
          <thead>
            <tr>
              <th>Item</th>
              <th>Category</th>
              <th>Price (LKR)</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${items}" var="item">
              <tr>
                <td>
                  <div class="item-name">${item.name}</div>
                  <div class="item-desc">${item.description}</div>
                </td>
                <td><span class="category-badge">${item.category}</span></td>
                <td class="price"><fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></td>
                <td>
                  <c:choose>
                    <c:when test="${item.available}">
                      <span class="status-badge available">Available</span>
                    </c:when>
                    <c:otherwise>
                      <span class="status-badge unavailable">Unavailable</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <div class="actions">
                    <a href="${pageContext.request.contextPath}/menuItems?action=edit&id=${item.id}"   class="btn-edit">Edit</a>
                    <a href="${pageContext.request.contextPath}/menuItems?action=delete&id=${item.id}" class="btn-delete">Delete</a>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:when>
      <c:otherwise>
        <div class="empty">
          <svg width="56" height="56" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"
                  d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"/>
          </svg>
          <p>No menu items found.</p>
          <a href="${pageContext.request.contextPath}/menuItems?action=add">Add your first item →</a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

</div>
</body>
</html>
