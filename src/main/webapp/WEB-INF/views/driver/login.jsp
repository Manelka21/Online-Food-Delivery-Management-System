<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Driver Login – FoodFleet</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen flex flex-col items-center justify-center p-6">

  <div class="w-full max-w-lg bg-white rounded-3xl shadow-sm border border-gray-200 p-10">
    <div class="text-center mb-8">
      <div class="text-6xl mb-">🚴</div>
      <h1 class="text-4xl font-extrabold text-gray-1800 tracking-tight">Delivery Driver Portal</h1>
      <p class="text-lg text-gray-800 mt-1">Sign in to your delivery account</p>
    </div>

    <c:if test="${not empty error}">
      <div class="p-4 bg-red-50 border border-red-200 rounded-2xl text-red-600 text-xs mb-6">⚠️ ${error}</div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="p-4 bg-green-50 border border-green-200 rounded-2xl text-green-600 text-xs mb-6">✅ ${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/driver/login" method="post" class="space-y-4">
      <div>
        <label class="block text-lg font-bold text-gray-800 mb-2" for="email">Email Address</label>
        <input type="email" id="email" name="email" class="w-full px-4 py-4 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-lg" placeholder="you@example.com" required/>
      </div>
      <div>
        <label class="block text-lg font-bold text-gray-800 mb-2" for="password">Password</label>
        <input type="password" id="password" name="password" class="w-full px-4 py-3 border border-gray-300 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition text-lg" placeholder="••••••••" required/>
      </div>
      <button type="submit" class="w-full bg-green-500 hover:bg-blue-800 text-white font-bold py-3.5 rounded-4xl transition shadow-sm text-xl">Sign In →</button>
    </form>

    <div class="my-6 flex items-center justify-between">
      <span class="w-1/5 border-b border-gray-200"></span>
      <span class="text-lg text-gray-600 uppercase tracking-widest">or</span>
      <span class="w-1/5 border-b border-gray-200"></span>
    </div>

    <div class="text-center text-xl text-gray-800">
      Don't have an account? <a href="${pageContext.request.contextPath}/driver/register" class="text-blue-500 font-bold hover:underline">Register here</a>
    </div>
  </div>
</body>
</html>