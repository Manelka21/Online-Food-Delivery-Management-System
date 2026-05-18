<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FoodFleet — Driver Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap">
    <style>
        /* Overriding the formal font globally on this page to prevent informal stretching */
        h1, h2, h3, h4, .font-heading {
            font-family: 'Inter', -apple-system, sans-serif !important;
            letter-spacing: -0.03em !important;
        }
    </style>
</head>
<body class="bg-[#f9fafb] font-sans antialiased text-gray-900">

    <div class="page-wrapper flex min-h-screen">
        <jsp:include page="sidebar.jsp" />

        <main class="main-content flex-1 p-16 ml-[120px] max-w-none w-full">

            <c:if test="${not empty success}">
                <div class="mb-8 p-5 bg-emerald-50 border border-emerald-200 text-emerald-700 rounded-2xl flex items-center gap-3 text-base font-semibold shadow-sm">
                    <span class="text-xl text-[#06C167]">✓</span> ${success}
                </div>
            </c:if>

            <div class="mb-12 border-b border-gray-100 pb-8">
                <h1 class="text-7xl font-extrabold tracking-tight text-gray-900">
                    Welcome to <span class="text-[#06C167]">FoodFleet</span>
                </h1>
                <p class="text-gray-500 text-xl mt-3 font-medium">How would you like to manage your platform operations today, ${driver.firstName}?</p>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-3 gap-10 w-full max-w-none items-stretch">

                <div class="bg-white rounded-[32px] shadow-sm border border-gray-400/60 p-8 flex flex-col justify-between min-h-[600px]">
                    <div>
                        <div class="flex items-center justify-between mb-6">
                            <h2 class="font-extrabold text-4xl text-gray-900 tracking-tight">Available Offers</h2>
                            <span class="w-3 h-3 rounded-full bg-[#06C167] animate-pulse"></span>
                        </div>

                        <c:if test="${empty activeOrder}">
                            <div class="p-6 bg-gray-50 rounded-2xl border border-gray-200/50 space-y-4">
                                <div class="flex justify-between items-start">
                                    <div>
                                        <span class="text-xs font-bold bg-[#06C167]/10 text-[#06C167] px-2.5 py-1 rounded-full uppercase tracking-wider">Ready for Pickup</span>
                                        <h3 class="text-xl font-extrabold text-gray-900 mt-2">McDonald's</h3>
                                        <p class="text-xs text-gray-400 font-bold uppercase tracking-wider">Rajagiriya Sub-hub</p>
                                    </div>
                                    <div class="text-right">
                                        <p class="text-[10px] font-bold text-gray-400 uppercase tracking-wider">Net Payout</p>
                                        <p class="text-2xl font-black text-gray-900 tracking-tight">LKR 450</p>
                                    </div>
                                </div>

                                <div class="space-y-2 border-t border-gray-200/60 pt-4 text-sm text-gray-600 font-medium">
                                    <p class="flex items-center gap-2 text-gray-700">📍 <span>123, Baseline Road, Colombo 05</span></p>
                                    <p class="flex items-center gap-2 text-gray-700">⏱️ <span>25 mins total</span></p>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty activeOrder}">
                            <div class="p-8 text-center bg-gray-50/50 rounded-2xl border border-dashed border-gray-200 flex flex-col items-center justify-center min-h-[200px]">
                                <p class="text-gray-400 text-sm font-semibold">Complete your active run to unlock the marketplace.</p>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${empty activeOrder}">
                        <div class="mt-6 pt-4 border-t border-gray-100 flex justify-end">
                            <form action="${pageContext.request.contextPath}/driver/acceptOrder" method="POST" class="w-full">
                                <input type="hidden" name="orderId" value="ORD-77123" />
                                <button type="submit" class="w-full px-6 py-3.5 bg-green-500 hover:bg-green-500 text-white text-lg font-bold rounded-full transition-all active:scale-95 shadow-md">
                                    Accept Offer
                                </button>
                            </form>
                        </div>
                    </c:if>
                </div>

                <div class="bg-white rounded-[32px] shadow-sm border border-gray-400/60 p-8 flex flex-col justify-between min-h-[600px]">
                    <div>
                        <div class="mb-6">
                            <h2 class="font-extrabold text-4xl text-gray-900 tracking-tight">Active Route Tracker</h2>
                        </div>

                        <c:if test="${not empty activeOrder}">
                            <div class="p-6 bg-emerald-50/40 rounded-2xl border border-emerald-100 space-y-4">
                                <div class="flex items-center justify-between">
                                    <span class="px-2.5 py-0.5 bg-[#06C167] text-white text-[10px] font-black rounded-full uppercase tracking-wider">In Progress</span>
                                    <span class="text-xs font-mono font-bold text-gray-400">ID: ${activeOrder}</span>
                                </div>
                                <h3 class="font-extrabold text-xl text-gray-900">KFC — Nugegoda</h3>
                                <p class="text-sm text-gray-600 font-medium">📍 Destination: SLIIT Malabe</p>
                                <div class="p-3 bg-white rounded-xl border border-emerald-100/70 text-xs text-[#06C167] font-bold flex items-center gap-2 shadow-sm">
                                    <span class="w-2 h-2 rounded-full bg-[#06C167] animate-ping"></span> Food is packed and ready.
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${empty activeOrder}">
                            <div class="p-8 text-center bg-gray-50/50 rounded-2xl border border-dashed border-gray-200 flex flex-col items-center justify-center min-h-[220px]">
                                <div class="w-14 h-14 bg-blue-50 text-blue-500 rounded-full flex items-center justify-center text-2xl mb-4">🛵</div>
                                <p class="text-gray-500 text-base font-bold">You are currently sitting idle</p>
                                <p class="text-xs text-gray-400 mt-1 max-w-xs mx-auto">Accept an open ticket from the live marketplace panel to begin tracking live dispatches.</p>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${not empty activeOrder}">
                        <form action="${pageContext.request.contextPath}/driver/completeOrder" method="POST" class="mt-6 pt-4 border-t border-gray-100">
                            <button type="submit" class="w-full px-6 py-3.5 bg-black hover:bg-gray-900 text-white text-sm font-bold rounded-full transition-all active:scale-95 text-center shadow-md">
                                Mark Drop As Completed ✓
                            </button>
                        </form>
                    </c:if>
                </div>

                <div class="bg-white rounded-[32px] shadow-sm border border-gray-400/60 p-8 flex flex-col justify-between min-h-[480px]">
                    <div>
                        <div class="mb-6">
                            <h2 class="font-extrabold text-4xl text-gray-900 tracking-tight">Duty Status</h2>
                        </div>

                        <div class="p-6 rounded-2xl border text-center mb-6 font-bold text-lg transition-all
                            ${empty activeOrder ? (driver.status == 'INACTIVE' ? 'bg-red-50 border-red-100 text-red-600' : 'bg-emerald-50 border-emerald-100 text-[#06C167]') : 'bg-amber-50 border-amber-100 text-amber-600'}">

                            <c:choose>
                                <c:when test="${not empty activeOrder}">
                                    ⚠️ Currently En Route to Delivery
                                </c:when>
                                <c:when test="${driver.status == 'INACTIVE'}">
                                    🔴 Offline (Not Accepting Orders)
                                </c:when>
                                <c:otherwise>
                                    🟢 Online (Accepting Deliveries)
                                </c:otherwise>
                             </c:choose>
                        </div>

                        <div class="flex flex-col gap-3">
                            <form action="${pageContext.request.contextPath}/driver/updateStatus" method="POST">
                                <input type="hidden" name="status" value="ACTIVE" />
                                <button type="submit" ${not empty activeOrder ? 'disabled' : ''}
                                    class="w-full px-5 py-3 rounded-xl border font-bold text-lg transition-all flex items-center justify-center
                                    ${not empty activeOrder ? 'opacity-40 cursor-not-allowed bg-gray-50 border-gray-100 text-gray-400' : (driver.status == 'ACTIVE' ? 'bg-[#06C167] border-[#06C167] text-white shadow-sm' : 'bg-white border-gray-200 text-gray-700 hover:bg-gray-50')}">
                                    <span>Accepting Deliveries</span>
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/driver/updateStatus" method="POST">
                                <input type="hidden" name="status" value="INACTIVE" />
                                <button type="submit" ${not empty activeOrder ? 'disabled' : ''}
                                    class="w-full px-5 py-3 rounded-xl border font-bold text-lg transition-all flex items-center justify-center
                                    ${not empty activeOrder ? 'opacity-40 cursor-not-allowed bg-gray-50 border-gray-100 text-gray-400' : (driver.status == 'INACTIVE' ? 'bg-red-600 border-red-600 text-white shadow-sm' : 'bg-white border-gray-200 text-gray-700 hover:bg-gray-50')}">
                                    <span>Off Duty Signout</span>
                                </button>
                            </form>

                            <div class="w-full px-5 py-3 rounded-xl border font-bold text-lg flex items-center justify-center transition-all
                                ${not empty activeOrder ? 'bg-amber-500 border-amber-500 text-white shadow-sm' : 'bg-gray-50 border-gray-100 text-gray-400 opacity-50'}">
                                <span>Currently on a Delivery Run</span>
                                <span class="text-xs">Busy</span>
                            </div>
                        </div>
                    </div>

                    <div class="text-[11px] text-gray-400 font-medium text-center border-t border-gray-100 pt-4">
                        System updates automatically upon processing drop requests.
                    </div>
                </div>

            </div>
        </main>
    </div>

</body>
</html>