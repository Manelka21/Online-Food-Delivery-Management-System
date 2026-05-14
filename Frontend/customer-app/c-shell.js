(function () {
  const API_BASE = 'http://localhost:8080';
  const currentPage = window.location.pathname.split('/').pop() || 'c-profile.html';
  const publicPages = ['c-login.html', 'c-signup.html'];

  function readCustomer() {
    try { return JSON.parse(localStorage.getItem('customer') || 'null'); }
    catch (error) { return null; }
  }

  const customer = readCustomer();
  let redirectTo = '';
  if (!customer && !publicPages.includes(currentPage)) redirectTo = 'c-login.html';
  if (customer && publicPages.includes(currentPage)) redirectTo = 'c-profile.html';

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

  window.customerApi = {
    baseUrl: API_BASE,
    get: (endpoint) => request(endpoint),
    post: (endpoint, body) => request(endpoint, { method: 'POST', body: JSON.stringify(body) }),
    put: (endpoint, body) => request(endpoint, { method: 'PUT', body: JSON.stringify(body) }),
    delete: (endpoint) => request(endpoint, { method: 'DELETE' })
  };

  window.getCustomerSession = readCustomer;
  window.saveCustomerSession = function (nextCustomer) {
    localStorage.setItem('customer', JSON.stringify(nextCustomer));
  };
  window.customerLogout = function () {
    localStorage.removeItem('customer');
    window.location.href = 'c-login.html';
  };

  function buildTopbar() {
    const mount = document.getElementById('customer-topbar');
    if (!mount || !readCustomer()) return;
    const user = readCustomer();
    mount.innerHTML = `
      <a class="brand" href="c-profile.html">FoodDash</a>
      <div class="nav-actions">
        <a class="nav-link ${currentPage === 'c-profile.html' ? 'active' : ''}" href="c-profile.html">My Profile</a>
        <span class="nav-link">${escapeHtml(user.name || 'Customer')}</span>
        <button class="btn-outline" type="button" onclick="customerLogout()">Logout</button>
      </div>
    `;
  }

  function escapeHtml(value) {
    return String(value ?? '').replace(/[&<>"]/g, function (char) {
      return ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' })[char];
    });
  }

  window.escapeCustomerHtml = escapeHtml;
  document.addEventListener('DOMContentLoaded', buildTopbar);
  if (redirectTo) window.location.href = redirectTo;
})();
