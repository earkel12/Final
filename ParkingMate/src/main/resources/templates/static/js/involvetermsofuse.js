document.addEventListener('DOMContentLoaded', function() {
    const agreeAllCheckbox = document.getElementById('agreeAllCheckbox');
    if (agreeAllCheckbox) {
        agreeAllCheckbox.addEventListener('change', function() {
            toggleAgreeAll(this);
        });
    }

    const form = document.getElementById('joinForm');
    if (form) {
        form.addEventListener('submit', function(e) {
            if (!validationAgreement()) {
                e.preventDefault();
            }
        });
    }
});

function toggleAgreeAll(checkbox) {
    const termsNames = ['terms1', 'terms2', 'terms3', 'terms4', 'terms5'];

    termsNames.forEach(name => {
        const agreeRadio = document.querySelector(`input[name="${name}"][value="agree"]`);
        const disagreeRadio = document.querySelector(`input[name="${name}"][value="disagree"]`);

        if (checkbox.checked) {
            if (agreeRadio) agreeRadio.checked = true;
        } else {
            if (agreeRadio) agreeRadio.checked = false;
            if (disagreeRadio) disagreeRadio.checked = false;
        }
    });
}

function validationAgreement() {
    const required = ['terms1', 'terms2', 'terms3', 'terms4'];
    let allAgreed = true;

    for (let name of required) {
        const selected = document.querySelector(`input[name="${name}"]:checked`);
        if (!selected || selected.value !== 'agree') {
            allAgreed = false;
            break;
        }
    }

    if (!allAgreed) {
        Swal.fire({
            icon: 'warning',
            title: '약관 동의 필요',
            text: '필수 약관에 동의하셔야 회원가입이 가능합니다.',
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        });
        return false;
    }
    return true;
}