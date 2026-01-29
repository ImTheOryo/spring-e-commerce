const toast = document.getElementById("toast");

export function showToast(type, title, message) {
    if (!toast) {
        console.error("Toast element not found");
        return;
    }

    // reset classes
    toast.className =
        "fixed top-6 right-6 z-50 hidden flex-col gap-0.5 p-4 w-72 md:w-96 rounded-xl shadow-lg transition-all duration-300";

    switch (type) {
        case "success":
            toast.classList.add("bg-emerald-100", "text-emerald-700", "border-emerald-100");
            break;
        case "error":
            toast.classList.add("bg-red-50", "text-red-700", "border-red-100");
            break;
        case "warning":
            toast.classList.add("bg-amber-50", "text-amber-700", "border-amber-100");
            break;
    }

    toast.querySelector("h3").innerText = title;
    toast.querySelector("p").innerText = message;

    toast.classList.remove("hidden");

    setTimeout(() => {
        toast.classList.add("hidden");
    }, 8000);
}
