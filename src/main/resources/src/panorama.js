import React from "react";
import * as fetch from "node-fetch";
import InputBar from "./inputBar";
import CircularProgress from '@material-ui/core/CircularProgress'
import {withStyles} from "@material-ui/core";

class Panorama extends React.Component {

    constructor(props){
        super(props);
        this.canvas = React.createRef();
        this.state = {
            panoramaBytes: [],
            height: default_height,
            width: default_width,
            lon: default_lon,
            lat: default_lat,
            elevation: default_elevation,
            azimuth: default_azimuth,
            fov: default_FOV,
            maxDist: default_maxDist,
            samplingExp: default_samplingExp,
            loading: false,
        };
        this.fetchImage = this.fetchImage.bind(this);
        this.fetchImageParam = this.fetchImageParam.bind(this);
        this.loadImage = this.loadImage.bind(this);
        this.inputChange = this.inputChange.bind(this);
    }

    fetchImage(){
        this.setState({loading: true});
        fetch('http://localhost:8080/getImage')
            .then(res => res.body)
            .then(body => new Response(body))
            .then(response => response.json())
            .then(data => {
                this.setState({
                    panoramaBytes: data,
                },
                    this.loadImage
                );
            })
            .catch(err => console.error(err));
    }

    fetchImageParam(){
        this.setState({loading: true});
        const h = this.state.height;
        const w = this.state.width;
        const lon = this.state.lon;
        const lat = this.state.lat;
        const e = this.state.elevation;
        const a = this.state.azimuth;
        const f = this.state.fov;
        const d = this.state.maxDist;
        const s = this.state.samplingExp;

        const url = 'http://localhost:8080/computePanorama' +
            '?longitude=' + lon +
            '&latitude=' + lat +
            '&elevation=' + e +
            '&azimuth=' + a +
            '&fieldOfView=' + f +
            '&maxDistance=' + d +
            '&imageWidth=' + w +
            '&imageHeight=' + h +
            '&samplingExponent=' + s;
        fetch(url)
            .then(res => res.body)
            .then(body => new Response(body))
            .then(response => response.json())
            .then(data => {
                this.setState({
                        panoramaBytes: data,
                    },
                    this.loadImage
                );
            })
            .catch(err => console.error(err));
    }

    loadImage(){
        this.setState({loading: false});
        const canvas = this.canvas.current;
        const ctx = canvas.getContext("2d");
        let w = this.props.width;
        let h = this.props.height;
        const imageData = ctx.createImageData(w, h);
        const data = imageData.data;
        const newData = this.state.panoramaBytes;
        for (let i = 0; i < newData.length; ++i){
            data[i] = newData[i];
        }
        ctx.putImageData(imageData, 0 ,0);
    }

    // Handles changes to the InputBar, which means handling changes to the values of the kernel choice as well as
    // the image url.
    inputChange = prop => event => {
        this.setState({ [prop]: event.target.value });
    }

    render() {
        const { classes } = this.props;
        return (
            <div>
                <div>
                    <InputBar
                        change={this.inputChange}
                        loadImage={this.fetchImageParam}
                        height={this.state.height}
                        width={this.state.width}
                        lon={this.state.lon}
                        lat={this.state.lat}
                        elevation={this.state.elevation}
                        azimuth={this.state.azimuth}
                        fov={this.state.fov}
                    />
                </div>
                <div>
                    <canvas
                        ref={this.canvas}
                        width={this.props.width}
                        height={this.props.height}
                    />
                </div>
            </div>
        );
    }

}

const default_height = 480;
const default_width = 1080;
const default_lon = 68087;
const default_lat = 470085;
const default_elevation = 1380;
const default_azimuth = 162;
const default_FOV = 27;

const default_maxDist = 300;
const default_samplingExp = 0;

const styles = theme => ({
    progress: {
        margin: theme.spacing.unit * 2,
    },
});

export default withStyles(styles)(Panorama);
