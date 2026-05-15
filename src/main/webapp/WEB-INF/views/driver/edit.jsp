<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Edit Profile – FoodFleet Driver</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen font-sans">
<c:set var="currentPage" value="edit"/>

<div class="flex h-screen overflow-hidden">
  <%@ include file="sidebar.jsp" %>

  <main class="flex-1 overflow-y-auto p-10">
    <div class="mb-10">
      <p class="text-lg text-gray-400 mb-2">
        <a href="${pageContext.request.contextPath}/driver/dashboard" class="hover:underline">Home</a> ›
        <a href="${pageContext.request.contextPath}/driver/profile" class="hover:underline">Profile</a> › Edit
      </p>
      <h1 class="text-4xl font-extrabold text-black tracking-tight">Edit Details</h1>
      <p class="text-gray-500 mt-1">Update your delivery driver information below.</p>
    </div>

    <c:if test="${not empty error}">
      <div class="p-4 bg-red-50 border border-red-200 rounded-2xl text-red-600 text-xs mb-6">⚠️ ${error}</div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="p-4 bg-green-50 border border-green-200 rounded-2xl text-green-600 text-xs mb-6">✅ ${success}</div>
    </c:if>

    <div class="bg-white border border-gray-200 rounded-3xl p-10 shadow-sm">
      <form action="${pageContext.request.contextPath}/driver/edit" method="post">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" value="${driver.firstName}" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition text-lg" required/>
          </div>

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" value="${driver.lastName}" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition text-lg" required/>
          </div>

          <div class="md:col-span-2">
            <label class="block text-lg font-bold text-gray-400 mb-2">Email Address <span class="text-gray-400 text-[12px]">(cannot be changed)</span></label>
            <input type="email" value="${driver.email}" disabled class="w-full px-4 py-3.5 border border-gray-200 rounded-2xl outline-none text-lg bg-gray-50 text-gray-400 cursor-not-allowed"/>
          </div>

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="phone">Phone Number</label>
            <input type="tel" id="phone" name="phone" value="${driver.phone}" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition text-lg" required/>
          </div>

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="vehicleType">Vehicle Type</label>
            <select id="vehicleType" name="vehicleType" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition bg-white text-lg" required>
              <option value="BIKE"      ${driver.vehicleType == 'BIKE'      ? 'selected' : ''}>🚲 Bicycle</option>
              <option value="SCOOTER"   ${driver.vehicleType == 'SCOOTER'   ? 'selected' : ''}>🛵 Scooter</option>
              <option value="MOTORBIKE" ${driver.vehicleType == 'MOTORBIKE' ? 'selected' : ''}>🏍️ Motorbike</option>
              <option value="CAR"       ${driver.vehicleType == 'CAR'       ? 'selected' : ''}>🚗 Car</option>
            </select>
          </div>

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="licenseNumber">License Number</label>
            <input type="text" id="licenseNumber" name="licenseNumber" value="${driver.licenseNumber}" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition text-lg" required/>
          </div>

          <div>
            <label class="block text-lg font-bold text-gray-400 mb-2" for="status">Availability Status</label>
            <select id="status" name="status" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition bg-white text-lg">
              <option value="ACTIVE"      ${driver.status == 'ACTIVE'      ? 'selected' : ''}>✅ Active</option>
              <option value="INACTIVE"    ${driver.status == 'INACTIVE'    ? 'selected' : ''}>⭕ Inactive</option>
              <option value="ON_DELIVERY" ${driver.status == 'ON_DELIVERY' ? 'selected' : ''}>🚴 On Delivery</option>
            </select>
          </div>

          <div class="md:col-span-2 border-t border-gray-100 pt-8 mt-4">
            <label class="block text-lg font-bold text-gray-400 mb-2" for="newPassword">New Password <span class="text-gray-400 text-[14px]">(leave blank to keep current)</span></label>
            <input type="password" id="newPassword" name="newPassword" class="w-full px-4 py-3.5 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 outline-none transition text-lg" placeholder="Enter new password to change it"/>
          </div>

        </div>

        <div class="flex gap-4 mt-10">
          <button type="submit" class="bg-black hover:bg-blue-600 text-white font-bold px-8 py-3.5 rounded-2xl transition shadow-sm text-xl">💾 Save Changes</button>
          <a href="${pageContext.request.contextPath}/driver/profile" class="bg-gray-100 hover:bg-gray-200 text-gray-600 font-bold px-6 py-3.5 rounded-2xl transition text-xl">Cancel</a>
        </div>
      </form>
    </div>
  </main>
</div>
</body>
</html>