function delFruit(fid) {
    if (confirm("是否确认删除？")) {
        window.location.href = 'fruit.do?fid=' + fid + '&operate=del';
    }
}
function page(pageOn){
    window.location.href = 'fruit.do?pageOn=' + pageOn;
}

