document.addEventListener("DOMContentLoaded", () => {
    const toast = document.querySelector(".toast")

    function showToast(type, title, message) {
        switch (type) {
            case "success" :
                toast.classList.add("text-green-500 bg-green-50")
                break
            case "error" :
                toast.classList.add("ext-red-500 bg-red-50")
                break
            case "warning" :
                toast.classList.add("text-green-500 bg-green-50")
                break
        }


        if(toast){
            toast.classList.add("show")
            setTimeout(() =>
                toast.classList.remove("show text-green-500 bg-green-5"),
                3000)  // 3s
        }
    }
});
