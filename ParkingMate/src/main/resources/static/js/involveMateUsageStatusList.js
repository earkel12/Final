function updateIntime(btn) {
    console.log("버튼 클릭됨");

    const prev = btn.previousElementSibling;
    if (!prev) {
        console.error("이 버튼 바로 위에 hidden input이 없습니다.");
        return;
    }

    const bookingnum = prev.value;
    console.log("bookingnum:", bookingnum);

    Swal.fire({
        title: '입차 처리',
        text: "입차 처리하시겠습니까?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#4B55E1',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 진행합니다',
        cancelButtonText: '취소'
    }).then((result) => {
        if (!result.isConfirmed) {
            console.log("사용자가 취소함");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/booking/updateIntime",
            data: { bookingnum: bookingnum },
            success: function(response) {
                if (response.success) {
                    Swal.fire({
                        icon: 'success',
                        title: '입차 완료',
                        text: '입차가 정상적으로 처리되었습니다.',
                        confirmButtonColor: '#4B55E1'
                    }).then(() => {
                        window.location.reload();
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '입차 실패',
                        text: response.error,
                        confirmButtonColor: '#4B55E1'
                    });
                    btn.disabled = false;
                }
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: '서버 오류',
                    text: error,
                    confirmButtonColor: '#4B55E1'
                });
                btn.disabled = false;
            }
        });
    });
    console.log("Intime ajax 호출 완료");
}

function updateOuttime(btn) {
    const bookingnum = btn.previousElementSibling.value;
    console.log("전달된 bookingnum:", bookingnum);

    Swal.fire({
        title: '출차 처리',
        text: "출차 처리하시겠습니까?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#4B55E1',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 진행합니다',
        cancelButtonText: '취소'
    }).then((result) => {
        if (!result.isConfirmed) {
            return;
        }

        $.ajax({
            type: "POST",
            url: "/booking/updateOuttime",
            data: { bookingnum: bookingnum },
            success: function(response) {
                if (response.success) {
                    Swal.fire({
                        icon: 'success',
                        title: '출차 완료',
                        html: `요금: <b>${response.price}원</b><br>이용 시간: <b>${response.minutes}분</b>`,
                        confirmButtonColor: '#4B55E1'
                    }).then(() => {
                        window.location.reload();
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '출차 실패',
                        text: response.error,
                        confirmButtonColor: '#4B55E1'
                    });
                    btn.disabled = false;
                }
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: '서버 오류',
                    text: error,
                    confirmButtonColor: '#4B55E1'
                });
                btn.disabled = false;
            }
        });
    });
    console.log("Outtime ajax 호출 완료");
}