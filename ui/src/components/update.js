import React from 'react';
import {Link, withRouter} from 'react-router-dom';
import '../form.css';

class Update extends React.Component {
    constructor(props) {
        super(props);
        this.state = {id: '', name: '', description: '', exchangeRate: '', products: [], totalPrice: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/v1/packages/' + this.props.match.params.id)
            .then(response => {
                return response.json();
            }).then(result => {
            this.setState({
                id: result.id,
                name: result.name,
                description: result.description,
                exchangeRate: result.exchangeRate,
                products: result.products,
                totalPrice: result.totalPrice
            });
        });
    }

    handleChange(event) {
        const state = this.state
        state[event.target.name] = event.target.value
        this.setState(state);
    }

    handleProductIdChange(event) {
        const state = this.state;
        state.products = event.target.value.split(",").map(s => s.trim());
        this.setState(state);
    }

    handleSubmit(event) {
        event.preventDefault();
        fetch('http://localhost:8080/api/v1/packages/' + this.props.match.params.id, {
            method: 'PUT',
            body: JSON.stringify({
                name: this.state.name,
                description: this.state.description,
                exchangeRate: this.state.exchangeRate,
                products: this.state.products,
                totalPrice: this.state.totalPrice

            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(response => {
            if (response.status === 200) {
                alert("Website update successfully.");
                window.location.replace("http://localhost:3000/");
            }
            if (response.status === 408) {
                alert("Check your internet connection");
            }
            if (response.status === 400) {
                alert("Your parameters are not valid");
            }
            if (response.status === 204) {
                alert("Check external rate service");
            }
        });
    }

    render() {
        return (
            <div id="container">

                <Link to="/">Packages</Link>
                <p/>
                <form onSubmit={this.handleSubmit}>
                    <input type="hidden" name="id" value={this.state.id}/>
                    <p>
                        <label>Name:</label>
                        <input type="text" name="name" value={this.state.name} onChange={this.handleChange}
                               placeholder="Name"/>
                    </p>
                    <p>
                        <label>Description:</label>
                        <input type="text" name="description" value={this.state.description}
                               onChange={this.handleChange} placeholder="Desc"/>
                    </p>
                    <p>
                        <label>exchangeRate:</label>
                        <select
                            type="text"
                            name="exchangeRate"
                            value={this.state.exchangeRate}
                            defaultValue={this.state.exchangeRate}
                            onChange={this.handleChange}
                            placeholder="USD or GBP or EURO"
                        >
                            <option value="USD">USD</option>
                            <option value="GBP">GBP</option>
                            <option value="EUR">EUR</option>
                        </select>
                    </p>
                    <p>
                        <label>productIds:</label>
                        <input type="text" name="products" value={this.state.products}
                               onChange={this.handleProductIdChange.bind(this)} placeholder="comma between ids"/>
                    </p>

                    <p>
                        <label>totalPrice:</label>
                        <input type="text" name="total prices" value={this.state.totalPrice}
                               onChange={this.handleChange} placeholder="totalPrice"/>
                    </p>

                    <p>
                        <input type="submit" value="Submit"/>
                    </p>
                </form>

            </div>
        );
    }


}

export default Update;