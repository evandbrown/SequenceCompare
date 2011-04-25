<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Protein Sequence Comparisons</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styles.css"></link>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"></script>
<script type="text/javascript">
	// Load protein comparisons from web service
	$(document).ready(function() {
		getComparisons();
	});

	var Comparisons = {};

	// Retrieve all protein comparisons from web service
	var getComparisons = function() {
		showInfoMessage("Retrieving Protein Comparisons...");

		var xhr = $.getJSON('comparisons/protein/list', function(data) {
			if (data === null || data.proteinComparisons === null) {
				showError("Could not retrieve list of protein comparisons.");
			}

			// If there's only one comparison, convert it into an array
			if (!$.isArray(data.proteinComparisons)) {
				data.proteinComparisons = [ data.proteinComparisons ];
			}

			Comparisons.proteins = data.proteinComparisons;

			displayComparisons(Comparisons.proteins);
		}).error(function() {
			showErrorMessage("Error loading protein comparisons.");
		}).success(function() {
			hideInfoMessage();
			showInterface();
		});
	};

	// Add protein comparisons retrieved from service to the ui
	var displayComparisons = function(comparisons) {
		// Get the comparison select box
		var select = $('#comparisons');

		// Add an item to the select box for each comparison
		for ( var i = 0; i < comparisons.length; i++) {
			var comparison = comparisons[i];
			select.append($("<option></option>").attr("value", i).text(
					comparison.sequenceOne.organism + " -vs- "
							+ comparison.sequenceTwo.organism));
		}

		// Wire an event handler to react when a selected item is changed
		select.change(function() {
			var selectedIndex = $(this).attr("selectedIndex");
			displayProteinComparison(selectedIndex);
		});

		select.change();
	};

	// Draw detailed view of a single protein comparison
	var displayProteinComparison = function(comparisonNumber) {
		var comparison = Comparisons.proteins[comparisonNumber];

		// Clear the text area where sequences are displayed
		$('#sequences').val('');

		// Populate information for protein 1
		$('#protein-1 .name').text(comparison.sequenceOne.name);
		$('#protein-1 .organism-name').text(comparison.sequenceOne.organism);

		// Populate information for protein 2
		$('#protein-2 .name').text(comparison.sequenceTwo.name);
		$('#protein-2 .organism-name').text(comparison.sequenceTwo.organism);

		// Draw alignment and distance info
		$('#sequences').val(comparison.alignment);
		$('#levenshtein-distance').text(comparison.levenshteinDistance);
	};

	// Display a message
	var showInfoMessage = function(message) {
		$('#messageBar').addClass('info');
		$('#messageBar').fadeIn();
		$('#messageBar').text(message);
	};

	// Hide a message
	var hideInfoMessage = function() {
		$('#messageBar').removeClass('info');
		$('#messageBar').fadeOut();
		$('#messageBar').text("");
	}

	// Display an error message
	var showErrorMessage = function(message) {
		$('#messageBar').addClass('error');
		$('#messageBar').fadeIn();
		$('#messageBar').text(message);
	};

	// Hide a message
	var hideErrorMessage = function() {
		$('#messageBar').removeClass('error');
		$('#messageBar').fadeOut();
		$('#messageBar').text("");
	};

	// Display the main UI
	var showInterface = function() {
		$('#container').fadeIn();
	};
</script>
</head>
<body>
	<div id="messageBar"></div>
	<div id="container">
		<label for="comparisons">Comparisons:</label><select
			name="comparisons" id="comparisons"></select><span id="add-new"><img src="images/add.png"></img></span>
		<div class="comparison">
			<div id="proteins">
				<div id="protein-1">
					<div>
						<label>Protein 1:</label><span class="name"></span>
					</div>
					<div>
						<label>Organism 1:</label><span class="organism-name"></span>
					</div>
				</div>
				<div id="protein-2"">
					<div>
						<label>Protein 2:</label><span class="name"></span>
					</div>
					<div>
						<label>Organism 2:</label><span class="organism-name"></span>
					</div>
				</div>
				<div style="clear: both"></div>
			</div>
			<div class="other">
				<div>
					<label>Alignment:</label>
					<textarea id="sequences" wrap="off" rows="3"></textarea>
				</div>
			</div>
			<div class="other">
				<label>Distance:</label><span id="levenshtein-distance"></span>
			</div>
		</div>
	</div>
</body>
</html>