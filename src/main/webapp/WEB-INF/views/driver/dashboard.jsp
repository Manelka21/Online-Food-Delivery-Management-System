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

        <main class="main-content flex-1 p-16 ml-[260px] w-full">

            <c:if test="${not empty success}">
                <div class="mb-8 p-5 bg-emerald-50 border border-emerald-200 text-emerald-700 rounded-2xl flex items-center gap-3 text-base font-semibold shadow-sm">
                    <span class="text-xl text-[#06C167]">✓</span> ${success}
                </div>
            </c:if>

            <div class="mb-12 border-b border-gray-100 pb-8">
                <h1 class="text-5xl font-extrabold tracking-tight text-gray-900">
                    Welcome to <span class="text-[#06C167]">FoodFleet</span>
                </h1>
                <p class="text-gray-500 text-xl mt-3 font-medium">How would you like to manage your platform operations today, ${driver.firstName}?</p>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-10 w-full items-stretch">

                <div class="bg-white rounded-[32px] shadow-sm border border-gray-500/60 p-10 flex flex-col justify-between min-h-[700px]">
                    <div>
                        <div class="flex items-center justify-between mb-8">
                            <h2 class="font-extrabold text-3xl text-gray-900 tracking-tight">Available Offers</h2>
                            <span class="w-3.5 h-3.5 rounded-full bg-[#06C167] animate-pulse"></span>
                        </div>

                        <c:if test="${empty activeOrder}">
                            <div class="p-8 bg-gray-50 rounded-2xl border border-gray-200/50 space-y-6">
                                <div class="flex justify-between items-start">
                                    <div>
                                        <span class="text-xs font-bold bg-[#06C167]/10 text-[#06C167] px-3 py-1 rounded-full uppercase tracking-wider">Ready for Pickup</span>
                                        <h3 class="text-2xl font-extrabold text-gray-900 mt-3">McDonald's</h3>
                                        <p class="text-sm text-gray-400 font-bold uppercase tracking-wider mt-0.5">Rajagiriya Sub-hub</p>
                                    </div>
                                    <div class="text-right">
                                        <p class="text-xs font-bold text-gray-400 uppercase tracking-wider">Net Payout</p>
                                        <p class="text-3xl font-black text-gray-900 tracking-tight mt-1">LKR 450.00</p>
                                    </div>
                                </div>

                                <div class="space-y-3 border-t border-gray-200/60 pt-6 text-base text-gray-600 font-medium">
                                    <p class="flex items-center gap-3 text-gray-700">
                                        <span class="text-xl">📍</span> <span><strong class="text-gray-900 font-bold">Drop-off:</strong> 123, Baseline Road, Colombo 05</span>
                                    </p>
                                    <p class="flex items-center gap-3 text-gray-700">
                                        <span class="text-xl">⏱️</span> <span><strong class="text-gray-900 font-bold">Est. Time:</strong> 25 mins total</span>
                                    </p>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty activeOrder}">
                            <div class="p-12 text-center bg-gray-50/50 rounded-2xl border border-dashed border-gray-200 flex flex-col items-center justify-center min-h-[250px]">
                                <p class="text-gray-400 text-base font-semibold">Complete your active delivery run to unlock the live marketplace entries.</p>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${empty activeOrder}">
                        <div class="mt-8 pt-6 border-t border-gray-100 flex justify-end">
                            <form action="${pageContext.request.contextPath}/driver/acceptOrder" method="POST" class="w-full md:w-auto">
                                <input type="hidden" name="orderId" value="ORD-77123" />
                                <button type="submit" class="w-full md:w-auto px-10 py-4 bg-green-500 hover:bg-gray-900 text-white text-lg font-bold rounded-full transition-all active:scale-95 shadow-md">
                                    Accept Offer
                                </button>
                            </form>
                        </div>
                    </c:if>
                </div>


                <div class="bg-white rounded-[32px] shadow-sm border border-gray-500/60 p-10 flex flex-col justify-between min-h-[700px]">
                    <div>
                        <div class="mb-8">
                            <h2 class="font-extrabold text-3xl text-gray-900 tracking-tight">Active Route Tracker</h2>
                        </div>

                        <c:if test="${not empty activeOrder}">
                            <div class="p-8 bg-emerald-50/40 rounded-2xl border border-emerald-100 space-y-6">
                                <div class="flex items-center justify-between">
                                    <span class="px-3.5 py-1 bg-[#06C167] text-white text-xs font-black rounded-full uppercase tracking-wider">In Progress</span>
                                    <span class="text-sm font-mono font-bold text-gray-400">ID: ${activeOrder}</span>
                                </div>

                                <div class="space-y-2">
                                    <h3 class="font-extrabold text-2xl text-gray-900">KFC — Nugegoda Complex</h3>
                                    <p class="text-base text-gray-600 font-medium">📍 <span class="font-bold text-gray-900">Destination:</span> Faculty of Computing, SLIIT Malabe</p>
                                </div>

                                <div class="p-4 bg-white rounded-xl border border-emerald-100/70 text-sm text-[#06C167] font-bold flex items-center gap-3 shadow-sm">
                                    <span class="w-2.5 h-2.5 rounded-full bg-[#06C167] inherit animate-ping"></span>
                                    Food is packed and ready for transport.
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${empty activeOrder}">
                            <div class="p-12 text-center bg-gray-50/50 rounded-2xl border border-dashed border-gray-200 flex flex-col items-center justify-center min-h-[300px]">
                                <div class="w-20 h-20 bg-blue-50 text-blue-500 rounded-full flex items-center justify-center text-4xl mb-6">🛵</div>
                                <p class="text-gray-500 text-lg font-bold">You are currently sitting idle</p>
                                <p class="text-sm text-gray-400 mt-2 max-w-sm font-medium">Accept an open ticket from the live marketplace panel to begin tracking live dispatches.</p>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${not empty activeOrder}">
                        <form action="${pageContext.request.contextPath}/driver/completeOrder" method="POST" class="mt-8 pt-6 border-t border-gray-100">
                            <button type="submit" class="w-full px-6 py-4 bg-green-500 hover:bg-gray-900 text-white text-base font-bold rounded-full transition-all active:scale-95 text-center shadow-md">
                                Mark Drop As Completed ✓
                            </button>
                        </form>
                    </c:if>
                </div>

            </div>
        </main>
    </div>

</body>
</html>