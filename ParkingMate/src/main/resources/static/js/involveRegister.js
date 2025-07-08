// 파킹메이트 회원가입/회원정보수정 전용

let isIdAvailable = false;

function validateForm() {
    const pwd = document.getElementById("pwd").value;
    const repwd = document.getElementById("repwd").value;
    const tel = document.querySelector('input[name="tel"]').value.trim();
    const resident1 = document.getElementById("resident_num1").value.trim();
    const resident2 = document.getElementById("resident_num2").value.trim();
    const emailId = document.querySelector('input[name="emailid"]').value.trim();
    const emailDomain = document.querySelector('select[name="emaildomain"]').value;

    if (pwd.length < 5) {
        Swal.fire({
            icon: 'error',
            title: '비밀번호 오류',
            text: '비밀번호는 5자리 이상이어야 합니다.'
        });
        document.getElementById("pwd").focus();
        return false;
    }

    if (pwd !== repwd) {
        Swal.fire({
            icon: 'error',
            title: '비밀번호 불일치',
            text: '비밀번호가 일치하지 않습니다.'
        });
        document.getElementById("repwd").focus();
        return false;
    }

    if (!isIdAvailable) {
        Swal.fire({
            icon: 'warning',
            title: '아이디 확인 필요',
            text: '아이디 중복 확인이 필요합니다.'
        });
        document.getElementById("id").focus();
        return false;
    }

    if (resident1.length !== 6 || resident2.length !== 7) {
        Swal.fire({
            icon: 'error',
            title: '주민등록번호 오류',
            text: '주민등록번호 앞 6자리, 뒤 7자리를 정확히 입력해주세요.'
        });
        return false;
    }

    const telRegex = /^\d{10,11}$/;
    if (!telRegex.test(tel)) {
        Swal.fire({
            icon: 'error',
            title: '전화번호 오류',
            text: '전화번호는 숫자만 포함하여 10~11자리로 입력해주세요.'
        });
        document.querySelector('input[name="tel"]').focus();
        return false;
    }

    if (!mergeEmail()) {
        return false;
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
function toggleCustomEmailInput() {
    const emailDomainSelect = document.getElementById("emaildomain");
    const customEmailInput = document.getElementById("customEmailDomain");

    if (emailDomainSelect.value === "custom") {
        customEmailInput.style.display = "inline-block";
        customEmailInput.required = true;
    } else {
        customEmailInput.style.display = "none";
        customEmailInput.value = "";
        customEmailInput.required = false;
    }
}
function mergeEmail() {
    const emailId = document.querySelector('input[name="emailid"]').value.trim();
    const emailDomainSelect = document.getElementById("emaildomain");
    let selectedDomain = emailDomainSelect.value;

    if (selectedDomain === "custom") {
        selectedDomain = document.getElementById("customEmailDomain").value.trim();
        if (!selectedDomain) {
            Swal.fire({
                icon: 'warning',
                title: '입력 필요',
                text: '이메일 도메인을 입력해주세요.'
            });
            return false;
        }
    }

    const fullEmail = `${emailId}@${selectedDomain}`;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(fullEmail)) {
        Swal.fire({
            icon: 'error',
            title: '이메일 오류',
            text: '유효한 이메일 형식으로 입력해주세요.'
        });
        return false;
    }

    document.getElementById("emailFull").value = fullEmail;
    return true;
}