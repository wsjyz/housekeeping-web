(function () {

   'use strict';

    /*global jQuery */
    jQuery(function($) {

        loadWithHash();

        // hashChange
        $(window).on('hashchange', function(event) {
            loadWithHash();
        });

        // 链接代理
        $('body').on('click', 'a', function(event) {
            var href = $(this).attr('href') || '',
                target = $(this).attr('target') || '';

            if (!href) {
                event.preventDefault();
                return;
            }

            if ((!target || target=='ajax') && (!href.match(/^javascript:/) || href.match(/^#/))) {
                event.preventDefault();
                document.location.hash = encodeURIComponent(href);
                loader(href);
            }
        })
        .on('click', '.main nav li', function(event) {
            $(this).addClass('active')
                .siblings('.active')
                .removeClass('active');
        });
    });

    /**
     * 根据Hash加载页面
     */
    function loadWithHash () {
        var hash = document.location.hash || "institution.html";
            hash = hash.replace(/^#/, '');

        // 加载默认页面m
        if (hash) {
            loader(decodeURIComponent(hash));
        }
    }

    /**
     * 页面加载器
     * @param  {string} url  地址
     * @param  {[type]} data 发送给后台的数据
     */
    function loader(url, data) {
        var prog = $('.progress'),
            cont = $('#content'),
            type = data ? 'post' : 'get',
            tips = '';

        $.ajax({
            url      : url,
            dataType : 'html',
            type     : type,
            data     : data,
            beforeSend: function() {
                prog.fadeIn();
            },
            complete: function() {
                prog.fadeOut();
            },
            success:function(html) {
                cont.html(html);
            },
            error: function() {
                var tips = '<div class="page-error alert alert-danger alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>错误！</strong>页面加载失败，<a href="'+ url +'">重新加载</a>。</div>';

                cont.html(tips);
            }
        });
    }

}(this));