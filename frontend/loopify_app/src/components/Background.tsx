import React from 'react';
import './Background.css';

function Background() {

    return (
        <div className='Background'>
           <text width="500" text-anchor="right">
                    <textPath xlinkHref="#curve" startOffset="12%">
                        LOOPIFYLOOPIFYLOOPIFYLOOPIFYLOOPIFYLOOPIFYLOOPIFYLOOPIFY
                    </textPath>
            </text>
        </div>
    );

}

export default Background;