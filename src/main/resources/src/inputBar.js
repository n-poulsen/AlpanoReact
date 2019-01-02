import React from "react";
import TextField from "@material-ui/core/TextField/TextField";
import { withStyles } from '@material-ui/core/styles';
import Button from "@material-ui/core/Button/Button";

class InputBar extends React.Component {

    // Renders an input bar
    render() {
        const { classes } = this.props;
        return (
            <div>
                <div>
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Longitude"
                        value={this.props.lon}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('lon')}
                    />
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Latitude"
                        value={this.props.lat}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('lat')}
                    />
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Elevation"
                        value={this.props.elevation}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('elevation')}
                    />
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Azimuth"
                        value={this.props.azimuth}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('azimuth')}
                    />
                </div>
                <div>
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Field of View"
                        value={this.props.fov}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('fov')}
                    />
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Width"
                        value={this.props.width}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('width')}
                    />
                    <TextField
                        className={classes.textField}
                        id="standard-helperText"
                        label="Height"
                        value={this.props.height}
                        helperText="Some important text"
                        margin="normal"
                        onChange={this.props.change('height')}
                    />
                    <Button
                        variant="contained"
                        size="large"
                        color="primary"
                        onClick={this.props.loadImage}
                    >
                        Load Panorama
                    </Button>
                </div>
            </div>

        );
    }

}

// The styles to apply to the input bar
const styles = theme => ({
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        marginBottom: theme.spacing.unit,
        width: 200,
    },
});

export default withStyles(styles)(InputBar);
