document.addEventListener("DOMContentLoaded", function () {
    const paymentBtn = document.getElementById("paymentBtn");
    if (!paymentBtn) return;

    paymentBtn.addEventListener("click", () => {
        tryPayment(); // 함수호출
    });
});

function tryPayment() {
    const lastpayText = document.getElementById('lastpay').textContent;
    const finalPrice = parseInt(lastpayText.replace(/[^0-9]/g, '') || "0", 10);

    // 차량번호 추출
    const activeTab = document.querySelector('.tab-item.active');
    if (!activeTab) {
        Swal.fire({
            icon: 'error',
            title: '차량 번호 오류',
            text: '선택된 차량 번호가 없습니다. 다시 시도해 주세요.',
            confirmButtonColor: '#4B55E1'
        });
        return;
    }
    const selectedCarNum = activeTab.textContent.trim();
    if (!selectedCarNum) {
        Swal.fire({
            icon: 'error',
            title: '차량 번호 오류',
            text: '차량 번호를 읽을 수 없습니다. 다시 시도해 주세요.',
            confirmButtonColor: '#4B55E1'
        });
        return;
    }
    console.log("selectedCarNum:", selectedCarNum);

    if (finalPrice === 0) {
        // 0원 결제 시 결제 생략 및 상태 업데이트
        Swal.fire({
            icon: 'info',
            title: '결제 생략',
            text: '결제금액이 0원이므로 결제없이 완료 처리됩니다.',
            confirmButtonColor: '#4B55E1'
        }).then(() => {
            $.ajax({
                url: "/finalpayment",
                method: "POST",
                data: { bookingcarnum: selectedCarNum },
                success: function () {
                    Swal.fire({
                        icon: 'success',
                        title: '결제 완료',
                        text: '결제가 정상적으로 완료되었습니다.',
                        confirmButtonColor: '#4B55E1'
                    }).then(() => {
                        window.location.href = "/";
                    });
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: '상태 업데이트 실패',
                        text: '결제 처리 후 상태 업데이트에 실패했습니다.',
                        confirmButtonColor: '#4B55E1'
                    });
                }
            });
        });
    } else {
        // 유료 결제 처리
        const IMP = window.IMP;
        IMP.init("imp84584038");

        IMP.request_pay({
            pg: "kakaopay",
            pay_method: "card",
            merchant_uid: "order_" + new Date().getTime(),
            name: "주차장 금액 결제",
            amount: finalPrice,
            buyer_email: "이수한@naver.com",
            buyer_name: "이수한",
            buyer_tel: "010-1234-5678",
            buyer_addr: "서울특별시",
            buyer_postcode: "12345"
        }, function (rsp) {
            if (rsp.success) {
                Swal.fire({
                    icon: 'success',
                    title: '결제 완료',
                    text: '결제가 정상적으로 완료되었습니다.',
                    confirmButtonColor: '#4B55E1'
                }).then(() => {
                    $.ajax({
                        url: "/finalpayment",
                        method: "POST",
                        data: { bookingcarnum: selectedCarNum },
                        success: function () {
                            window.location.href = "/";
                        },
                        error: function () {
                            Swal.fire({
                                icon: 'error',
                                title: '상태 업데이트 실패',
                                text: '결제 처리 후 상태 업데이트에 실패했습니다.',
                                confirmButtonColor: '#4B55E1'
                            });
                        }
                    });
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '결제 실패',
                    text: rsp.error_msg,
                    confirmButtonColor: '#4B55E1'
                });
            }
        });
    }
}