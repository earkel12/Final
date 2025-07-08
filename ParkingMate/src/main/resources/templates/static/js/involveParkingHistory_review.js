function goToFilter(type) {
    if (type === 'all') {
        window.location.href = '/myParkingHistory';
    } else if (type === 'valet') {
        window.location.href = '/myParkingMateHistory';
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".review-btn");

    buttons.forEach(btn => {
      btn.addEventListener("click", () => {
        const num = btn.dataset.bookingnum;
        const date = encodeURIComponent(btn.dataset.bookingdate);
        const name = encodeURIComponent(btn.dataset.parkinglot_name);

        console.log("리뷰쓰기 클릭:", num, date, name);

        window.location.href = `/reviewWrite?bookingnum=${num}&bookingdate=${date}&parkinglot_name=${name}`;
      });
    });
});
