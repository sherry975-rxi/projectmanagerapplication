import React from 'react'
import ReduxToastr from 'react-redux-toastr'
import 'react-redux-toastr/lib/css/react-redux-toastr.min.css'

export default props => {
    return (
        <ReduxToastr
            timeOut={4000}
            position='top-right'
            newestOnTop={false}
            preventDuplicates={true}
            transitionIn='fadeIn'
            transitionOut='fadeOut'
            progressBar />)
}