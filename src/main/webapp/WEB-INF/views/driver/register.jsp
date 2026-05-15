<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Driver Register – FoodFleet</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen flex flex-col items-center justify-center p-6">

  <div class="w-full max-w-lg bg-white rounded-3xl shadow-sm border border-gray-200 p-10">
    <div class="text-center mb-8">
      <div class="text-4xl mb-4">🚴</div>
      <h1 class="text-2xl font-extrabold text-gray-900 tracking-tight">Join as Driver</h1>
      <p class="text-sm text-gray-400 mt-1">Create your delivery driver account</p>
    </div>

    <c:if test="${not empty error}">
      <div class="p-4 bg-red-50 border border-red-200 rounded-2xl text-red-600 text-xs mb-6">⚠️ ${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/driver/register" method="post" class="space-y-5">
      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <div>
          <label class="block text-xs font-bold text-gray-400 mb-2" for="firstName">First Name</label>
          <input type="text" id="firstName" name="firstName" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="John" required/>
        </div>

        <div>
          <label class="block text-xs font-bold text-gray-400 mb-2" for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="Doe" required/>
        </div>
      </div>

      <div>
        <label class="block text-xs font-bold text-gray-400 mb-2" for="email">Email Address</label>
        <input type="email" id="email" name="email" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="you@example.com" required/>
      </div>

      <div>
        <label class="block text-xs font-bold text-gray-400 mb-2" for="password">Password</label>
        <input type="password" id="password" name="password" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="Min. 6 characters" required/>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
        <div>
          <label class="block text-xs font-bold text-gray-400 mb-2" for="phone">Phone Number</label>
          <input type="tel" id="phone" name="phone" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="+94 77 000 0000" required/>
        </div>

        <div>
          <label class="block text-xs font-bold text-gray-400 mb-2" for="vehicleType">Vehicle Type</label>
          <select id="vehicleType" name="vehicleType" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition bg-white text-sm" required>
            <option value="">Select vehicle</option>
            <option value="BIKE">🚲 Bicycle</option>
            <option value="SCOOTER">🛵 Scooter</option>
            <option value="MOTORBIKE">🏍️ Motorbike</option>
            <option value="CAR">🚗 Car</option>
          </select>
        </div>
      </div>

      <div>
        <label class="block text-xs font-bold text-gray-400 mb-2" for="licenseNumber">License Number</label>
        <input type="text" id="licenseNumber" name="licenseNumber" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-sm" placeholder="e.g. B1234567" required/>
      </div>

      <button type="submit" class="w-full bg-black hover:bg-blue-600 text-white font-bold py-3.5 rounded-2xl transition shadow-sm text-sm mt-2">
        Create Account →
      </button>
    </form>

    <div class="text-center text-xs text-gray-500 mt-6 pt-6 border-t border-gray-100">
      Already have an account? <a href="${pageContext.request.contextPath}/driver/login" class="text-blue-500 font-bold hover:underline">Sign in</a>
    </div>
  </div>
</body>
</html>