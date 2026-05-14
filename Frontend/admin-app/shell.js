(function () {
  const API_BASE = 'http://localhost:8080';
  const currentPage = window.location.pathname.split('/').pop() || 'dashboard.html';
  const adminUser = JSON.parse(sessionStorage.getItem('adminUser') || 'null');

  let redirectTo = '';
  const publicPages = ['login.html', 'signup.html'];
  
  if (!adminUser && !publicPages.includes(currentPage)) {
    redirectTo = 'login.html';
  } else if (adminUser) {
    if (adminUser.role !== 'ADMIN' && !publicPages.includes(currentPage)) {
      sessionStorage.removeItem('adminUser');
      redirectTo = 'login.html';
    } else if (publicPages.includes(currentPage)) {
      redirectTo = 'dashboard.html';
    }
  }

  async function request(endpoint, options = {}) {
    const response = await fetch(API_BASE + endpoint, {
      headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
      ...options
    });
    const contentType = response.headers.get('content-type') || '';
    const payload = contentType.includes('application/json') ? await response.json() : null;
    if (!response.ok) {
      const message = payload && payload.error ? payload.error : 'Request failed.';
      throw new Error(message);
    }
    return payload;
  }

  window.apiClient = {
    get: (endpoint) => request(endpoint),
    post: (endpoint, body) => request(endpoint, { method: 'POST', body: JSON.stringify(body) }),
    put: (endpoint, body) => request(endpoint, { method: 'PUT', body: JSON.stringify(body) }),
    delete: (endpoint) => request(endpoint, { method: 'DELETE' })
  };

  window.adminLogout = function () {
    sessionStorage.removeItem('adminUser');
    window.location.href = 'login.html';
  };

  function buildNav() {
    const sidebar = document.getElementById('sidebar');
    if (!sidebar) return;
    sidebar.innerHTML = `
      <div class="sidebar-brand">FoodDash Admin</div>
      <nav class="sidebar-nav" aria-label="Admin navigation">
        <a href="dashboard.html" class="nav-item ${currentPage === 'dashboard.html' ? 'active' : ''}">Dashboard</a>
        <a href="customers.html" class="nav-item ${currentPage === 'customers.html' ? 'active' : ''}">Customers</a>
      </nav>
      <button class="logout-btn" type="button" onclick="adminLogout()">Logout</button>
    `;
  }

  window.escapeAdminHtml = function (value) {
    return String(value ?? '').replace(/[&<>"]/g, function (char) {
      return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' })[char];
    });
  };

  document.addEventListener('DOMContentLoaded', buildNav);
  if (redirectTo) window.location.href = redirectTo;
})();
