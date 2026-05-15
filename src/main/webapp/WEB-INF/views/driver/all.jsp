<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>All Drivers – FoodFleet</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen font-sans">
<c:set var="currentPage" value="all"/>

<div class="flex h-screen overflow-hidden">
  <%@ include file="sidebar.jsp" %>

  <main class="flex-1 overflow-y-auto p-10">
    <div class="mb-10">
      <p class="text-xs text-gray-400 mb-2"><a href="${pageContext.request.contextPath}/driver/dashboard" class="hover:underline">Home</a> › All Drivers</p>
      <h1 class="text-4xl font-extrabold text-black tracking-tight">All Drivers</h1>
      <p class="text-gray-500 mt-1">Complete list of registered delivery drivers.</p>
    </div>

    <div class="bg-white border border-gray-200 rounded-3xl shadow-sm overflow-hidden p-8">
      <c:choose>
        <c:when test="${empty drivers}">
          <div class="text-center py-16 text-gray-400">
            <div class="text-4xl mb-4">🚴</div>
            <p class="text-base font-semibold">No drivers registered yet.</p>
          </div>
        </c:when>
        <c:otherwise>
          <div class="flex justify-between items-center mb-8">
            <h2 class="text-sm font-extrabold text-gray-400 uppercase">
              ${drivers.size()} driver${drivers.size() != 1 ? 's' : ''} registered
            </h2>
            <a href="${pageContext.request.contextPath}/driver/register" class="bg-black hover:bg-blue-600 text-white text-xs font-bold px-6 py-3 rounded-full transition shadow-sm">
              + Add Driver
            </a>
          </div>

          <div class="overflow-x-auto">
            <table class="w-full text-left border-collapse text-sm">
              <thead>
                <tr class="bg-gray-50 border-b border-gray-200 text-gray-400 text-xs font-bold uppercase tracking-wider">
                  <th class="py-5 px-6">Driver ID</th>
                  <th class="py-5 px-6">Name</th>
                  <th class="py-5 px-6">Email</th>
                  <th class="py-5 px-6">Phone</th>
                  <th class="py-5 px-6">Vehicle</th>
                  <th class="py-5 px-6">License</th>
                  <th class="py-5 px-6">Status</th>
                  <th class="py-5 px-6">Joined</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-100 text-gray-700">
                <c:forEach var="d" items="${drivers}">
                  <tr class="hover:bg-gray-50/50 transition">
                    <td class="py-4 px-6 font-extrabold text-blue-600">${d.driverId}</td>
                    <td class="py-4 px-6">
                      <div class="flex items-center gap-4">
                        <div class="w-8 h-8 bg-gray-100 text-black font-black text-xs rounded-full flex items-center justify-center uppercase">
                          ${d.firstName.substring(0,1)}${d.lastName.substring(0,1)}
                        </div>
                        <span class="font-semibold text-gray-900">${d.firstName} ${d.lastName}</span>
                      </div>
                    </td>
                    <td class="py-4 px-6 text-gray-400">${d.email}</td>
                    <td class="py-4 px-6 font-medium">${d.phone}</td>
                    <td class="py-4 px-6 font-semibold flex items-center gap-2">
                      <c:choose>
                        <c:when test="${d.vehicleType == 'BIKE'}">🚲</c:when>
                        <c:when test="${d.vehicleType == 'SCOOTER'}">🛵</c:when>
                        <c:when test="${d.vehicleType == 'MOTORBIKE'}">🏍️</c:when>
                        <c:when test="${d.vehicleType == 'CAR'}">🚗</c:when>
                      </c:choose>
                      ${d.vehicleType}
                    </td>
                    <td class="py-4 px-6 font-mono text-xs">${d.licenseNumber}</td>
                    <td class="py-4 px-6">
                      <c:choose>
                        <c:when test="${d.status == 'ACTIVE'}">
                          <span class="bg-green-50 text-green-600 font-semibold px-3 py-1 rounded-full text-xs">Active</span>
                        </c:when>
                        <c:when test="${d.status == 'ON_DELIVERY'}">
                          <span class="bg-orange-50 text-orange-600 font-semibold px-3 py-1 rounded-full text-xs">On Delivery</span>
                        </c:when>
                        <c:otherwise>
                          <span class="bg-gray-100 text-gray-500 font-semibold px-3 py-1 rounded-full text-xs">Inactive</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td class="py-4 px-6 text-gray-400 text-xs">${d.registeredDate}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </main>
</div>
</body>
</html>