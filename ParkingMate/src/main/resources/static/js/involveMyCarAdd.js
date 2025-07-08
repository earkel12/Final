document.addEventListener("DOMContentLoaded", () => {

    window.handleSelection = function(type, value) {
        if (type === 'gear') {
            document.getElementById("gear").value = value;
            document.getElementById("manualBtn").style.backgroundColor = (value === '수동') ? "#ccc" : "";
            document.getElementById("autoBtn").style.backgroundColor = (value === '자동') ? "#ccc" : "";
        } else if (type === 'gas') {
            document.getElementById("gas_type").value = value;
        }
    }

    window.validateForm = function() {
        const carNum = document.getElementById('car_num').value.trim();
        const modelName = document.getElementById('modelname').value.trim();
        const modelYear = document.getElementById('model_year').value.trim();
        const color = document.getElementById('color').value.trim();
        const gear = document.getElementById('gear').value;
        const gasType = document.getElementById('gas_type').value;

        if (!carNum) {
            Swal.fire({
                icon: 'warning',
                title: '차량번호 입력 필요',
                text: '차량번호를 입력해주세요.'
            }).then(() => {
                document.getElementById('car_num').focus();
            });
            return false;
        }

        if (!modelName) {
            Swal.fire({
                icon: 'warning',
                title: '차량 모델명 입력 필요',
                text: '차량 모델명을 입력해주세요.'
            }).then(() => {
                document.getElementById('modelname').focus();
            });
            return false;
        }

        if (!modelYear) {
            Swal.fire({
                icon: 'warning',
                title: '연식 입력 필요',
                text: '차량 연식을 입력해주세요.'
            }).then(() => {
                document.getElementById('model_year').focus();
            });
            return false;
        }

        if (!gasType) {
            Swal.fire({
                icon: 'warning',
                title: '유종 선택 필요',
                text: '유종을 선택해주세요.'
            });
            return false;
        }

        if (!color) {
            Swal.fire({
                icon: 'warning',
                title: '차량 색상 입력 필요',
                text: '차량 색상을 입력해주세요.'
            }).then(() => {
                document.getElementById('color').focus();
            });
            return false;
        }

        if (!gear) {
            Swal.fire({
                icon: 'warning',
                title: '수동/자동 선택 필요',
                text: '수동/자동을 선택해주세요.'
            });
            return false;
        }

        return true;
    }

    window.openFindPopup = function(){
        window.open('/toFindCarPopup', '차량검색팝업', 'width=600, height=400');
    }
});