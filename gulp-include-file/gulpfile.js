'use-strict';

const gulp = require('gulp'),
    config = require('./config');

gulp.task('default', function() {
    console.log(config.paths.app);
    console.log(config.paths.assets);
    console.log(config.paths.build);
});
