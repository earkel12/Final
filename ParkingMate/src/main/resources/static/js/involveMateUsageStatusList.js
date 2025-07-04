/**
 * 
 */

function updateIntime(btn) {
	console.log("버튼 클릭됨");

	    const prev = btn.previousElementSibling;
	    if (!prev) {
	        console.error("이 버튼 바로 위에 hidden input이 없습니다.");
	        return;
	    }

	    const bookingnum = prev.value;
	    console.log("bookingnum:", bookingnum);

	    if (!confirm("입차 처리하시겠습니까?")) {
	        console.log("사용자가 취소함");
	        return;
	    }

    $.ajax({
        type: "POST",
        url: "/booking/updateIntime",
        data: { bookingnum: bookingnum },
        success: function(response) {
            if (response.success) {
				$(".status-box span").text("입차 완료!");
                console.log("서버 응답:", response);
				window.location.href = window.location.href;
				return;
            } else {
                alert("입차 실패: " + response.error);
                btn.disabled = false;
            }
        },
        error: function(xhr, status, error) {
            alert("서버 오류: " + error);
            btn.disabled = false;
        }
    });
    console.log("Intime ajax 호출 완료");
}

function updateOuttime(btn) {
	// 바로 앞의 hidden input 값 가져오기
	const bookingnum = btn.previousElementSibling.value;
	console.log("전달된 bookingnum:", bookingnum);
	
    if (!confirm("출차 처리하시겠습니까?")) return;

    $.ajax({
        type: "POST",
        url: "/booking/updateOuttime",
        data: { bookingnum: bookingnum },
        success: function(response) {
            if (response.success) {
				$(".status-box span").text("출차 완료! 요금: " + response.price + "원, " + response.minutes + "분 이용");
                alert(
                    "출차 완료!\n" +
                    "요금: " + response.price + "원\n" +
                    "이용 시간: " + response.minutes + "분"
                );
                console.log("서버 응답:", response);
				window.location.href = window.location.href; // ✅ 이 부분 추가 (리프레시 처리)
				return;
            } else {
                alert("출차 실패: " + response.error);
                btn.disabled = false;
            }
        },
        error: function(xhr, status, error) {
            alert("서버 오류: " + error);
            btn.disabled = false;
        }
    });
    console.log("Outtime ajax 호출 완료");
}