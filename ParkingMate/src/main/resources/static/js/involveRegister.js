//파킹메이트 회원가입/회원정보수정 전용

let isIdAvailable = false;

function validateForm() {
    const pwd = document.getElementById("pwd").value;
    const repwd = document.getElementById("repwd").value;

    if (pwd !== repwd) {
        alert("비밀번호가 일치하지 않습니다.");
        document.getElementById("repwd").focus();
        return false;
    }

    if (!isIdAvailable) {
        alert("아이디 중복 확인이 필요합니다.");
        document.getElementById("id").focus();
        return false;
    }
	
	if (!mergeEmail()) {
	        return false; // 이메일 입력이 취소되면 제출 방지
	    }

    return true;
}

function checkId() {
    const idInput = document.getElementById("id");
    const id = idInput.value.trim();
    const msgSpan = document.getElementById("id-check-msg");

    if (id === "") {
        msgSpan.textContent = "아이디를 입력하세요.";
        msgSpan.style.color = "red";
        return;
    }

    fetch("/checkId?id=" + encodeURIComponent(id))
        .then(response => response.text())
        .then(data => {
            if (data.includes("사용 가능")) {
                msgSpan.textContent = data;
                msgSpan.style.color = "green";
                isIdAvailable = true;
            } else {
                msgSpan.textContent = data;
                msgSpan.style.color = "red";
                isIdAvailable = false;
            }
        })
        .catch(error => {
            msgSpan.textContent = "오류 발생: " + error;
            msgSpan.style.color = "red";
            isIdAvailable = false;
        });
}

function matchPwd() {
    const pwdInput = document.getElementById("pwd");
    const repwdInput = document.getElementById("repwd");
    const msgSpan = document.getElementById("pwd-check-msg");

    if (pwdInput.value === "" && repwdInput.value === "") {
        msgSpan.textContent = "비밀번호를 입력하세요.";
        msgSpan.style.color = "red";
    } else if (pwdInput.value === repwdInput.value) {
        msgSpan.textContent = "비밀번호가 일치합니다.";
        msgSpan.style.color = "green";
    } else {
        msgSpan.textContent = "비밀번호가 일치하지 않습니다.";
        msgSpan.style.color = "red";
    }
}

function mergeResidentNum() {
    const front = document.getElementById("resident_num1").value.replace(/[^0-9]/g, "");
    const back = document.getElementById("resident_num2").value.replace(/[^0-9]/g, "");
    const hiddenInput = document.getElementById("resident_num");

    if (front.length <= 6 && back.length <= 7) {
        hiddenInput.value = front + "-" + back;
    }
}
function mergeEmail() {
	// 이메일 아이디와 도메인 합치기
	const emailId = document.querySelector('input[name="emailid"]').value.trim();
	const emailDomainSelect = document.querySelector('select[name="emaildomain"]');
	const selectedDomain = emailDomainSelect.value;

	let emailDomain = selectedDomain;
	if (selectedDomain === "custom") {
	    // 직접 입력 선택 시 사용자 입력 받기
	    emailDomain = prompt("사용하실 이메일 도메인을 직접 입력해주세요:");
	if (!emailDomain) {
	    alert("이메일 도메인을 입력해주세요.");
	    return false;
	}
	    emailDomain = emailDomain.trim();
	} 

	const fullEmail = `${emailId}@${emailDomain}`;
	document.getElementById("emailFull").value = fullEmail;

	return true;
}