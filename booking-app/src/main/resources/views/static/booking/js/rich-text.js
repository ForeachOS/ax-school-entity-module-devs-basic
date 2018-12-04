EntityModule.registerInitializer( function ( node ) {
    $( '[rich-text]', node ).each( function () {
        ClassicEditor.create( this );
    } );
} );