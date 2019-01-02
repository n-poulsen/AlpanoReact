import React from "react";
import { withStyles } from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress'

class LoadingCanvas extends React.Component {

    constructor(props){
        super(props);
        this.canvas = React.createRef();
        this.loadImage = this.loadImage.bind(this);
    }

    loadImage(){
        const canvas = this.canvas.current;
        const ctx = canvas.getContext("2d");
        let w = this.props.width;
        let h = this.props.height;
        const imageData = ctx.createImageData(w, h);
        const data = imageData.data;
        const newData = this.props.panoramaBytes;
        for (let i = 0; i < newData.length; ++i){
            data[i] = newData[i];
        }
        ctx.putImageData(imageData, 0 ,0);
        console.log("done loading");
        this.props.doneLoading();
    }

    render() {
        const { classes } = this.props;
        if (this.props.loading){
            return (
                <div>
                    <CircularProgress className={classes.progress} />
                </div>
            )
        }else{
            return (
                <div>
                    <canvas
                        ref={this.canvas}
                        width={this.props.width}
                        height={this.props.height}
                    />
                </div>
            )
        }
    }

}

const styles = theme => ({
    progress: {
        margin: theme.spacing.unit * 2,
    },
});

export default withStyles(styles)(LoadingCanvas);