<div class="box-footer no-padding" style="margin-top: -20px;">
	<div style="padding: 5px;">
		<div id="page" style="background: #fff; border: 0px; margin-top: 0px; padding: 2px; height: 25px;">
			<div style="width: 40%; float: left;">
				<div class="pageInfo" style="margin-left: 5px;">
					共<span>${pageInfo.total}</span>条 | 每页<span>${pageInfo.size}</span>条
					| 共<span>${pageInfo.pages}</span>页
				</div>
			</div>
			<div style="width: 60%; float: left;">
				<div class="pageOperation">
					<#if (pageInfo.pageNum>1)>
						<a href="javascript:page_nav(document.forms[0],1);" class="btn btn-sm btn-default no-padding"
						   style="width: 30px; height: 20px;"> <span class="glyphicon glyphicon-backward"></span></a>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.prePage});"
						   class="btn btn-sm btn-default no-padding" style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-triangle-left"></span></a>
					<#else>
						<a href="javascript:page_nav(document.forms[0],1);"
						   class="btn btn-sm btn-default no-padding" disabled="disabled"
						   style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-backward"></span></a>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.prePage});"
						   class="btn btn-sm btn-default no-padding" disabled="disabled"
						   style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-triangle-left"></span></a>
					</#if>
					<a disabled="disabled" class="btn btn-default no-padding"
					   style="width: 30px; height: 20px;">${pageInfo.pageNum}</a>
					<#if (pageInfo.pageNum<pageInfo.pages)>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.nextPage});"
						   class="btn btn-sm btn-default no-padding" style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-triangle-right"></span></a>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.pages});"
						   class="btn btn-sm btn-default no-padding" style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-forward"></span></a>
					<#else>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.nextPage});"
						   class="btn btn-sm btn-default no-padding" disabled="disabled"
						   style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-triangle-right"></span></a>
						<a href="javascript:page_nav(document.forms[0],${pageInfo.pages});"
						   class="btn btn-sm btn-default no-padding" disabled="disabled"
						   style="width: 30px; height: 20px;"> <span
									class="glyphicon glyphicon-forward"></span></a>
					</#if>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function page_nav(frm, num) {
		// alert(frm.pageIndex.value)
		frm.pageIndex.value = num;
		frm.submit();
	}
	/* 查找 */
	$('.baseKetsubmit').on('click', function() {
		var baseKey = $('.baseKey').val();
		console.log(baseKey);
		$('.thistable').load('${url}?baseKey='+baseKey+'${(catalog)!''}');
	});
</script>
