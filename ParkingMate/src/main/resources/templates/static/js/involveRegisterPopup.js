// 파킹메이트 비밀번호 변경 팝업 전용

function validatePwd() {
	const currentPwd = document.getElementById("currentPwd").value;
    const pwd = document.getElementById("pwd").value;
    const confirmPwd = document.getElementById("confirmPwd").value;
    
    if (!currentPwd || !newPwd || !confirmPwd) {
        alert("모든 항목을 입력해주세요.");
        return false;
    }
    if (pwd === "" || confirmPwd === "") {
        alert("비밀번호를 입력하세요.");
        return false;
    }
    if (pwd !== confirmPwd) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    return true;
}
function matchPwd() {
	const pwdInput = document.getElementById("pwd");
	const confirmPwdInput = document.getElementById("confirmPwd");
	const msgSpan2 = document.getElementById("pwd-check-msg");
	
	if(pwdInput.value=="" && confirmPwdInput.value=="") {
		msgSpan2.textContent = "비밀번호를 입력하세요.";
		msgSpan2.style.color = "red";
	} else if(pwdInput.value==confirmPwdInput.value) {
		msgSpan2.textContent = "비밀번호가 일치합니다.";
		msgSpan2.style.color = "green";
	} else {
		msgSpan2.textContent = "비밀번호가 일치하지 않습니다.";
		msgSpan2.style.color = "red";
	}
	
}