const app = Vue.
createApp({
    data(){
        return{
            accountType:"",
        }
    },
    created(){
        console.log("aaaaaaaaaaaa");
    },
    methods:{
        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        }, 
        postAccounts(){
            axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                console.log('tarjeta creada');
                window.location.href="/Web/accounts.html"
            })
            .catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    swal({
                        title: "error",
                        text: error.response.data, 
                        icon: "error",
                    })
                }}
            );
        }, 
    },
    computed:{  
    },
}).mount('#app')