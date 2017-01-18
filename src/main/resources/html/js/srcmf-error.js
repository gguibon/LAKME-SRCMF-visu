$( document ).ready(function() {
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	$('.values1').each(function() {
		window.call.submit(this.id,this.value);
	});  

	//popover
	$('[data-toggle="popover"]').popover({ trigger: "hover" });
	//popover end
});


$('#start').click(function(event){
	var header = $('#deleteHeader').is(':checked');
	call.tsv2MaltEval(header, $('input[name=id]').val(),$('input[name=form]').val(),$('input[name=lemma]').val(),
		$('input[name=cpostag]').val(),$('input[name=postag]').val(),$('input[name=feats]').val(),$('input[name=head]').val()
		,$('input[name=deprel]').val(),$('input[name=phead]').val(),$('input[name=pdeprel]').val());
});

$('#startErrorViewer').click(function(event){
	var header = $('#deleteHeader2').is(':checked');
	var scoresPos = $('#posScoresSwitch').is(':checked');
	var scoresSyn = $('#synScoresSwitch').is(':checked');
	var tag = $('#tags').find(":selected").text();
	call.ErrorViewer(header, tag, scoresPos, scoresSyn, $('input[name=id]').val(),$('input[name=form]').val(),$('input[name=lemma]').val(),
		$('input[name=cpostag]').val(),$('input[name=postag]').val(),$('input[name=feats]').val(),$('input[name=head]').val()
		,$('input[name=deprel]').val(),$('input[name=phead]').val(),$('input[name=pdeprel]').val());
});

$('#showScores').click(function(event){
	var header = $('#deleteHeader2').is(':checked');
	call.getSynScores(header);
});

$('#refresh').click(function(event){
	var header = $('#deleteHeader2').is(':checked');
	var col = $('input[name=deprel]').val();
	call.setListTags('alertfcerror','tags', header, col);
});

$('#alertfcerror').bind("DOMSubtreeModified",function(){
	var header = $('#deleteHeader2').is(':checked');
	var col = $('input[name=deprel]').val();
	call.setListTags('alertfcerror','tags', header, col);
});

$('#exportError').click(function(event){
	var header = $('#deleteHeader2').is(':checked');
	var context = $('#contextExport').is(':checked');
	call.export(header, context);
});

$("#srcmf,#malt,#mate").each(function(){
	$(this).click(function(event){
		var header = $('#deleteHeader2').is(':checked');
		var col = $('input[name=deprel]').val();
		call.setListTags('alertfcerror','tags', header, col);
	});
});

//arborator
$('#reloadSent').click(function(event){
	var header = $('#deleteHeader3').is(':checked');
	call.reloadSentences(header, $('input[name=id]').val(),$('input[name=form]').val(),$('input[name=lemma]').val(),
		$('input[name=cpostag]').val(),$('input[name=postag]').val(),$('input[name=feats]').val(),$('input[name=head]').val()
		,$('input[name=deprel]').val(),$('input[name=phead]').val(),$('input[name=pdeprel]').val());
});

$('#emptySent').click(function(event){
	call.deleteSentences();
});

$('#exportChanges').click(function(event){
	var header = $('#deleteHeader3').is(':checked');
	call.exportChanges(header);
});
//arborator end