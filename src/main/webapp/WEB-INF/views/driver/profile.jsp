<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>My Profile – FoodFleet Driver</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen font-sans">
<c:set var="currentPage" value="profile"/>

<div class="flex h-screen overflow-hidden">
  <%@ include file="sidebar.jsp" %>

  <main class="flex-1 overflow-y-auto p-10">
    <div class="mb-10">
      <p class="text-xs text-gray-400 mb-2"><a href="${pageContext.request.contextPath}/driver/dashboard" class="hover:underline">Home</a> › Profile</p>
      <h1 class="text-4xl font-extrabold text-black tracking-tight">My Profile</h1>
      <p class="text-gray-500 mt-1">View your registered delivery driver details.</p>
    </div>

    <c:if test="${not empty success}">
      <div class="p-4 bg-green-50 border border-green-200 rounded-2xl text-green-600 text-xs mb-6">✅ ${success}</div>
    </c:if>

    <div class="bg-white border border-gray-200 rounded-3xl p-8 mb-8 shadow-sm">
      <div class="flex items-center justify-between border-b border-gray-100 pb-8 mb-8">
        <div class="flex items-center gap-6">
          <div class="w-20 h-20 bg-black text-white rounded-full flex items-center justify-center font-black text-2xl uppercase">
            ${driver.firstName.substring(0,1)}${driver.lastName.substring(0,1)}
          </div>
          <div>
            <h2 class="text-2xl font-extrabold text-gray-900">${driver.firstName} ${driver.lastName}</h2>
            <div class="flex items-center gap-4 mt-2">
              <span class="text-sm text-gray-400">${driver.driverId}</span>
              <c:choose>
                <c:when test="${driver.status == 'ACTIVE'}">
                  <span class="bg-green-50 text-green-600 font-semibold px-3 py-1 rounded-full text-xs">Active</span>
                </c:when>
                <c:when test="${driver.status == 'ON_DELIVERY'}">
                  <span class="bg-orange-50 text-orange-600 font-semibold px-3 py-1 rounded-full text-xs">On Delivery</span>
                </c:when>
                <c:otherwise>
                  <span class="bg-gray-100 text-gray-400 font-semibold px-3 py-1 rounded-full text-xs">Inactive</span>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
        <a href="${pageContext.request.contextPath}/driver/edit" class="bg-black hover:bg-blue-600 text-white font-bold px-6 py-3 rounded-full transition text-xs shadow-sm">
          ✏️ Edit Profile
        </a>
      </div>

      <div class="divide-y divide-gray-100 text-lg">
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Driver ID</span>
          <span class="font-extrabold text-gray-900">${driver.driverId}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">First Name</span>
          <span class="font-semibold text-gray-800">${driver.firstName}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Last Name</span>
          <span class="font-semibold text-gray-800">${driver.lastName}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Email Address</span>
          <span class="font-semibold text-gray-800">${driver.email}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Phone Number</span>
          <span class="font-semibold text-gray-800">${driver.phone}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Vehicle Type</span>
          <span class="font-semibold text-gray-800">
            <c:choose>
              <c:when test="${driver.vehicleType == 'BIKE'}">🚲 Bicycle</c:when>
              <c:when test="${driver.vehicleType == 'SCOOTER'}">🛵 Scooter</c:when>
              <c:when test="${driver.vehicleType == 'MOTORBIKE'}">🏍️ Motorbike</c:when>
              <c:when test="${driver.vehicleType == 'CAR'}">🚗 Car</c:when>
              <c:otherwise>${driver.vehicleType}</c:otherwise>
            </c:choose>
          </span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">License Number</span>
          <span class="font-semibold text-gray-800">${driver.licenseNumber}</span>
        </div>
        <div class="py-4 flex justify-between">
          <span class="font-bold text-gray-400">Registered Date</span>
          <span class="font-semibold text-gray-800">${driver.registeredDate}</span>
        </div>
      </div>
    </div>

    <div class="bg-red-50 border border-red-200 rounded-4xl p-8 shadow-sm">
      <h2 class="text-2xl font-bold text-red-600 mb-4">⚠️ Delete Account</h2>
      <p class="text-red-500 text-lg mb-6 max-w-xl leading-relaxed">
        Permanently delete your account and all associated data. This action cannot be undone.
      </p>
      <a href="${pageContext.request.contextPath}/driver/delete" class="bg-red-600 hover:bg-red-700 text-white font-bold px-6 py-3 rounded-full text-xl transition">
        🗑️ Delete Account
      </a>
    </div>
  </main>
</div>
</body>
</html>