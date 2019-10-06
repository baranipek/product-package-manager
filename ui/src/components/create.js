import React from 'react';
import {Link} from 'react-router-dom';

class Create extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: '', description: '', exchangeRate: '', products: []};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleProductIdChange = this.handleProductIdChange.bind(this);
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

        fetch('http://localhost:8080/api/v1/packages', {
            method: 'POST',
            body: JSON.stringify({
                name: this.state.name,
                description: this.state.description,
                products: this.state.products,
                exchangeRate: this.state.exchangeRate
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(response => {
            if (response.status === 201) {
                alert("New package saved successfully");
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
                            defaultValue={this.state.exchangeRate}
                            onChange={this.handleChange}
                            placeholder="USD or GBP or EUR"
                        >
                            <option value="USD">Select Currency</option>
                            <option value="GBP">GBP</option>
                            <option value="EUR">EUR</option>
                        </select>
                    </p>

                    <p>
                        <label>productIds:</label>
                        <input type="text" name="products" value={this.state.products}
                               onChange={this.handleProductIdChange} placeholder="comma between ids"/>
                    </p>

                    <p>
                        <input type="submit" value="Submit"/>
                    </p>
                </form>
            </div>
        );
    }
}

export default Create;