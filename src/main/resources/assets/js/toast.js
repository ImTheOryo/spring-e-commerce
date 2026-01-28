document.addEventListener("DOMContentLoaded", () => {
    const toast = document.querySelector(".toast")

    function showToast(type, title, message) {
        switch (type) {
            case "success" :
                toast.classList.add("text-green-500 bg-green-50")
                break
            case "error" :
                toast.classList.add("text-red-500 bg-red-50")
                break
            case "warning" :
                toast.classList.add("text-green-700 bg-amber-100")
                break
        }

        if(toast){
            toast.classList.add("show")
            setTimeout(() =>
                toast.classList.remove("show"),
                toast.classList.remove("text-green-500"),
                toast.classList.remove("bg-green-5"),
                toast.classList.remove("text-red-500"),
                toast.classList.remove("bg-red-50"),
                toast.classList.remove("text-green-700"),
                toast.classList.remove("bg-amber-100"),
                10000)  // 1Os
        }
    }
});
