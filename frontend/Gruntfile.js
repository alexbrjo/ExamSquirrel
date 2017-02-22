module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        basepath: {
            app: 'src',
            dist: 'build'
        },
        clean:{
            build: ['build/*']
        },
        concat: {
            options: {
                separator: ';'
            },
            build: {
                src: [ 'src/**/*' ],
                dest: 'build/examMaker.js'
            }
        },
        uglify: {
            build: {
                files: {
                    'build/examMaker.min.js': [ 'build/examMaker.js' ]
                }
            }
        },
        copy:{
            build:{
                cwd: 'src',
                src: [ '**' ],
                dest: 'build',
                expand: true
            },
            dist:{
                cwd: 'build/',
                src: [ '*.min.js' ],
                // Move minized Frontend Files to webapp folder
                dest: '../backend/src/main/webapp/rsc',
                expand: true
            }
        },
        jasmine : {
            src : ['src/**/*.js', '!src/App.js'],
                options : {
                    specs : 'test/**/*.test.js'
                }
            }
    });
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jasmine');
    grunt.registerTask(
        'build', 
        'cleans, copys to build folder and uglifies', 
        ['clean', 'copy:build', 'concat:build', 'uglify:build']
    );
    grunt.registerTask(
        'run', 
        'builds and copies result to ../backend project', 
        ['clean', 'copy:build', 'concat:build', 'uglify:build', 'copy:dist']
    );
};
