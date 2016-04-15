$( document ).ready(function() {
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

   	$('.values1').each(function() {
  		window.call.submit(this.id,this.value);
	});  

// $('#sidePanel').offcanvas(placement : 'left',
// 	autohide : true,
// 	canvas : true
// 	);

	//popover
	$('[data-toggle="popover"]').popover({ trigger: "hover" });
	//popover end

// slider (for later to have a slider panel with centered settings)
// $('#slider').slideReveal({
//   trigger: $("#trigger"),
//   overlay : true,
//   push: false,
//   width: "70%",
//   top: 40
// });
// slider end

});


$('#start').click(function(event){
	var header = $('#deleteHeader').is(':checked');
	call.tsv2MaltEval(header);
	});

$('#startErrorViewer').click(function(event){
	var header = $('#deleteHeader2').is(':checked');
	var scoresPos = $('#posScoresSwitch').is(':checked');
	var scoresSyn = $('#synScoresSwitch').is(':checked');
	var tag = $('#tags').find(":selected").text();
	call.ErrorViewer(header, tag, scoresPos, scoresSyn);
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
	call.reloadSentences(header);
});

$('#emptySent').click(function(event){
	call.deleteSentences();
});

$('#exportChanges').click(function(event){
	call.exportChanges();
});

//arborator end

// utiliser ce morceau en java et get le chemin d'un script contenu dans un fichier temp
// permettra de gérer le loading?




//call functions in a SYNCHRONOUS way 
		// function Typer(callback)
		// {
		//     $.blockUI({message: '<h1>Loading!</h1>'});
		//     return true;
		// }

		// function typer2(callback){
		// 	// var srcText = 'EXAMPLE 2';
		//  //    var i = 0;
		//  //    var result = srcText[i];
		//  //    var interval = setInterval(function() {
		//  //        if(i == srcText.length - 1) {
		//  //            clearInterval(interval);
		//  //            callback();
		//  //            return;
		//  //        }
		//  //        i++;
		//  //        result += srcText[i].replace("\n", "<br />");
		//  //        $("#message2").html(result);
		//  //    },
		//  //    100);
		// 	var header = $('#deleteHeader').is(':checked');
		// 	call.synScores(header);
		//     return true;
		// }

		// function playBGM (callback) {
		//     // alert("Play BGM function");
		//     // $('#bgm').get(0).play();
		//     $.unblockUI();
		// }

		// Typer(function () {
		    
		//     	typer2(function () {
		//       		playBGM();
		//       });
		    
		// });
// Synchronous end
