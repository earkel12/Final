// checkSession.js - 모든 페이지 공통 세션 확인 후 없으면 로그인 페이지로 리다이렉트

document.addEventListener("DOMContentLoaded", function () {
    fetch('/checkSession')
        .then(response => {
            if (!response.ok) {
                // 400, 401, 403 등 오류 발생 시 즉시 로그인 페이지로 이동
                console.error('세션 확인 실패, 상태코드:', response.status);
                window.location.href = '/login';
                return;
            }
            return response.json();
        })
        .then(data => {
            if (data && !data.loggedIn) {
                // 세션 없을 경우 SweetAlert 후 로그인 페이지로 이동
                Swal.fire({
                    icon: 'warning',
                    title: '로그인 필요',
                    text: '로그인 후 이용해 주세요.',
                    confirmButtonColor: '#4B55E1',
                    confirmButtonText: '로그인 페이지로 이동'
                }).then(() => {
                    window.location.href = '/login';
                });
            }
        })
        .catch(error => {
            console.error('세션 확인 중 오류 발생:', error);
            // fetch 오류 발생 시에도 로그인 페이지로 이동
            window.location.href = '/login';
        });
});