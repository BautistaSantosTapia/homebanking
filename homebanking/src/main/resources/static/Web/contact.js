const app = Vue.
createApp({
    data(){
        return{

        }
    },
    created(){
        console.log("cccccccccccc");
    },
    methods:{
        postCerrar(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
    },
    computed:{
        
    },
}).mount('#app')