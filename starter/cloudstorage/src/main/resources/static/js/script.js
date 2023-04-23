// For opening the modals

function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

$(document).ready(function() {
  // Add event listener to the note form
  $('#note-form').submit(function(e) {
    e.preventDefault(); // Prevent form from submitting normally
    var formData = $(this).serialize(); // Get form data
    var url = $(this).attr('action'); // Get form action URL
    // Send AJAX request to submit form data
    $.ajax({
      type: 'POST',
      url: url,
      data: formData,
      success: function(data) {
        // Update note tab content with new data
        // This assumes that the server returns HTML to update the tab content
        console.log("data: ", data);
        $('#noteTabContent').html(data);
        // Close the modal
        $('#noteModal').modal('hide');
      },
      error: function() {
        alert('Error submitting form!');
      }
    });
  });
  });

function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

// To stay on the html tab where the api request was made
//$(document).ready(function() {
//  // Set the active tab on click
//  $('#nav-tab a').on('click', function(e) {
//    e.preventDefault();
//    $(this).tab('show');
//    var tabId = $(this).attr('aria-controls');
//    // Set the active tab ID using Thymeleaf expressions
//    /*[[# th:utext('${activeTab}')]]*/document.cookie = "activeTab=" + tabId;
//  });
//
//  // Restore the active tab on page load
//  var activeTab = /*[[${activeTab}]]*/null;
//  if (activeTab) {
//    $('#' + activeTab).tab('show');
//  }
//});

// Get cookie value by name
function getCookie(name) {
  var value = "; " + document.cookie;
  var parts = value.split("; " + name + "=");
  if (parts.length == 2) return parts.pop().split(";").shift();
}



/*
*  to check if jquery ($) is working properly (should return a function if it is)
(function($) {
  // Your jQuery code goes here
  console.log($); // should return a function if it works
})(jQuery);
*/
/*
* for dark theme mode
*/


//const btn = document.querySelector(".btn-toggle");
//const prefersDarkScheme = window.matchMedia("(prefers-color-scheme: dark)");
//
//const currentTheme = localStorage.getItem("theme");
//if (currentTheme == "dark") {
//  document.body.classList.toggle("dark-theme");
//} else if (currentTheme == "light") {
//  document.body.classList.toggle("light-theme");
//}
//
//btn.addEventListener("click", function () {
//  if (prefersDarkScheme.matches) {
//    document.body.classList.toggle("light-theme");
//    let theme = document.body.classList.contains("light-theme")
//      ? "light"
//      : "dark";
//  } else {
//    document.body.classList.toggle("dark-theme");
//    let theme = document.body.classList.contains("dark-theme")
//      ? "dark"
//      : "light";
//  }
//  localStorage.setItem("theme", theme);
//});
/*

trying with ajax, but tabs still behave the same
$('.form-submit').on('submit', function(e) {
    e.preventDefault(); // prevent the form from submitting normally
    let form = $(this);
    let url = form.attr('action');
    let formData = form.serialize(); // serialize the form data

    // Submit the form using AJAX
    $.ajax({
        type: 'POST',
        url: url,
        data: formData,
        success: function(data) {
            // Do something here if the submission was successful
            // For example, you could show a success message
            console.log('Form submission successful');
        },
        error: function(xhr, status, error) {
            // Do something here if the submission failed
            // For example, you could show an error message
            console.log('Form submission failed: ' + error);
        }
    });
});
*/