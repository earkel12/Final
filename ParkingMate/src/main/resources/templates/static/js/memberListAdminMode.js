document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchInput");
    const searchBtn = document.getElementById("searchBtn");

    // 버튼 클릭 시 검색
    searchBtn.addEventListener("click", filterTable);

    // 입력창에서 Enter 눌렀을 때 검색
    searchInput.addEventListener("keyup", function (event) {
        if (event.key === "Enter" || event.keyCode === 13) {
            filterTable();
        }
    });

    function filterTable() {
        const input = searchInput.value.toLowerCase();
        const rows = document.querySelectorAll("tbody tr");

        rows.forEach(row => {
            const cells = row.querySelectorAll("td");
            let match = false;
            cells.forEach((cell, index) => {
                if ([0, 1, 3].includes(index) && cell.textContent.toLowerCase().includes(input)) {
                    match = true;
                }
            });
            row.style.display = match ? "" : "none";
        });
    }
});